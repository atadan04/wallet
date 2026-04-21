FROM eclipse-temurin:17-jdk-alpine
LABEL authors="Komlev"
WORKDIR /app
COPY ../wallet1/target/wallet-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080


ENTRYPOINT ["java","-jar","app.jar"]