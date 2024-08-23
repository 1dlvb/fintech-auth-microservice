FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/auth-0.0.1-SNAPSHOT.jar /app/auth.jar
ENTRYPOINT ["java", "-jar", "/app/auth.jar"]