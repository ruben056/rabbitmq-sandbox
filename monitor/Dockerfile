#stage 1
#Start with a base image containing Java runtime
FROM openjdk:17-slim as build
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
