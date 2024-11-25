FROM maven:3-eclipse-temurin-23 AS build

WORKDIR /build

COPY pom.xml .
COPY src src

RUN mvn clean install -DskipTests

FROM eclipse-temurin:23-alpine

ARG configuration=development
ENV SPRING_PROFILES_ACTIVE=$configuration

WORKDIR /app

COPY --from=build /build/target/*.jar server.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "server.jar"]
