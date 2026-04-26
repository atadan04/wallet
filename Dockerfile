FROM amazoncorretto:17-alpine
LABEL authors="Komlev"
WORKDIR /app
COPY ../build/libs/wallet-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080


ENTRYPOINT ["java","-jar","app.jar"]