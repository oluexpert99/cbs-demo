FROM openjdk:17-oracle
COPY target/*.jar /app/cbs-app.jar
WORKDIR /app
EXPOSE 80
ENTRYPOINT ["java", "-jar", "cbs-app.jar"]