# Dockerfile
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copia el jar generado por Spring Boot
COPY target/boot-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
