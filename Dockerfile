FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/demo-api/target/quarkus-app/lib/ ./lib/
COPY --from=build /app/demo-api/target/quarkus-app/quarkus-run.jar ./
COPY --from=build /app/demo-api/target/quarkus-app/app/ ./app/
COPY --from=build /app/demo-api/target/quarkus-app/quarkus/ ./quarkus/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]