# Stage 1: Build the application
FROM gradle:7-jdk17-alpine as builder
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon

# Stage 2: Create runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]