FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY xmlapi/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]