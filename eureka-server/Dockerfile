# Use Maven image with JDK 22 (early-access version for JDK 22)
FROM eclipse-temurin:22-jdk-alpine as builder

# Set the working directory
WORKDIR /app

# Copy Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN ./mvn clean package -DskipTests

# Use a lightweight Java 22 runtime for the final image
FROM eclipse-temurin:22-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar eureka-server.jar

# Expose the Eureka Server's default port
EXPOSE 8761

# Command to run the application
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
