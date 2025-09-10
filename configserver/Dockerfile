FROM openjdk:21-jdk-slim
# Install curl for the healthcheck
RUN apt-get update && apt-get install -y curl
# Add the application's jar to the container
ARG JAR_FILE=target/configserver-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
# execute the application
ENTRYPOINT ["java","-jar","/app.jar"]