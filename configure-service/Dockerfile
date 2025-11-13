FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/configure-service-*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]