FROM openjdk:17-jdk-alpine as builder

WORKDIR /app
COPY . /app

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

COPY --from=builder /app/target/mazes-1.0.jar /mazes.jar

CMD ["java", "-jar", "/mazes.jar"]
