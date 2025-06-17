FROM eclipse-temurin:21-jdk as builder
WORKDIR /app
COPY boot-order-service/pom.xml .
COPY boot-order-service/src ./src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]