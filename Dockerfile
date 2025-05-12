FROM eclipse-temurin:21 AS build

WORKDIR /app

COPY code/.mvn .mvn
COPY code/mvnw .
COPY code/pom.xml .
COPY code/boot ./boot
COPY code/infrastructure ./infrastructure
COPY code/rest-ports ./rest-ports
COPY code/domain ./domain
COPY code/usecases ./usecases
COPY api-contracts ./api-contracts

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests
RUN ls -l /app/boot/target

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app
COPY --from=build /app/boot/target/boot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
