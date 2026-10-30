package com.sidecar.logging_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;
import java.util.Objects;

@ConfigurationProperties(prefix = "sidecar")
public record SidecarProperties(String watchFile) {
    public SidecarProperties {
        Objects.requireNonNull(watchFile, "sidecar.watch-file must be configured");
    }

    public Path watchPath() {
        return Path.of(watchFile).toAbsolutePath().normalize();
    }
}
