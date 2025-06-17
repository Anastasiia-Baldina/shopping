FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# 1. Копируем ВЕСЬ проект (включая все модули)
COPY . .

# 2. Собираем только нужный модуль с зависимостями
RUN ./mvnw package -pl boot-shop-web -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/boot-shop-web/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]