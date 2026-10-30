package com.sidecar.logging_service.service;

import com.sidecar.logging_service.config.SidecarProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoggingService {

    private final SidecarProperties properties;
    private long offset = 0;

    @Scheduled(fixedDelay = 2000)
    public void processLog() {
        Path file = properties.watchPath();
        if (!Files.exists(file)) {
            log.debug("Waiting for log file {}", file.toAbsolutePath());
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(file.toFile(), "r")) {
            if (raf.length() < offset) {   // file rotated or truncated
                offset = 0;
            }
            raf.seek(offset);

            String line;
            while ((line = raf.readLine()) != null) {
                String decoded = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                ship(decoded);
            }
            offset = raf.getFilePointer();
        } catch (IOException e) {
            log.error("Failed to tail {}", file, e);
        }
    }

    private void ship(String line) {
        // later: push to Kafka / Elasticsearch / Splunk
        log.info("Shipping: {}", line);
    }
}
