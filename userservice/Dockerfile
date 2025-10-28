# Dockerfile for user-service
FROM gradle:8.10.0-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 9899
CMD ["java", "-jar", "app.jar"]
