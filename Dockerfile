FROM openjdk:17-jdk-slim

WORKDIR /app

ARG JAR_FILE_NAME=backend-0.0.1-SNAPSHOT.jar
COPY build/libs/${JAR_FILE_NAME} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]