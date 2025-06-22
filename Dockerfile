FROM openjdk:17-jdk-slim AS build

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw.cmd .

RUN apt-get update && apt-get install -y maven

COPY src src

RUN mvn clean package -DskipTests

FROM openjdk:17-jre-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/app/app.jar"]