FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the jar file from the configuration module
COPY configuration/target/configuration-0.0.1-SNAPSHOT.jar mobiauto-backend.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "mobiauto-backend.jar"]