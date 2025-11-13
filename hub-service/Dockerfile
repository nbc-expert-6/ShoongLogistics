FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/hub-service-*.jar app.jar
EXPOSE 19092
ENTRYPOINT ["java", "-jar", "app.jar"]