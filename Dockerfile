# === Stage 1: Build ===
FROM gradle:7.6.4-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon

# === Stage 2: Run ===
FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
