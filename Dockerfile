FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/notificacao-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8082
CMD ["java", "-jar", "/app/app.jar"]