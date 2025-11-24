# -- Build --
FROM --platform=linux/amd64 gradle:8.5-jdk17 AS builder
WORKDIR /build

COPY build.gradle settings.gradle ./
COPY gradle ./gradle

RUN gradle dependencies --no-daemon

COPY . .
RUN gradle clean build -x test --no-daemon

# -- Runtime --
FROM amazoncorretto:17-alpine

COPY --from=builder /build/build/libs/*.jar /app/service.jar

ENV JAVA_OPTS="-Xms256m -Xmx512m"

EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/service.jar"]