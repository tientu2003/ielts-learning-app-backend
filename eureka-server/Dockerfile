FROM openjdk:21-jre-slim

WORKDIR /app
COPY target/*.jar /app/eureka-server.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
