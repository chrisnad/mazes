FROM openjdk:17-jdk-alpine as builder

WORKDIR /app
COPY . /app

# required for strip-debug to work
RUN apk add --no-cache binutils

# Build small JRE image
RUN jlink \
         --add-modules ALL-MODULE-PATH \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /jre

RUN ./mvnw clean package -DskipTests

FROM alpine:latest

ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

COPY --from=builder /jre $JAVA_HOME

COPY --from=builder /app/target/mazes-1.0.jar /mazes.jar

CMD ["java", "-jar", "/mazes.jar"]
