FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S -g 1000 app && adduser -S -u 1000 -G app app \
 && mkdir -p /var/log/app && chown app:app /var/log/app
WORKDIR /app
COPY target/*.jar app.jar
USER app

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]