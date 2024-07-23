FROM maven:3.9.5 AS build
COPY . /app
WORKDIR /app
RUN mvn package -e -X -DskipTests

FROM openjdk:8
COPY --from=build /app/target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
