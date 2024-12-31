FROM openjdk:25-oraclelinux9

WORKDIR /app
COPY target/*.jar /app/reading-service.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "reading-service.jar"]
