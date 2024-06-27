FROM eclipse-temurin:17-jdk-alpine
USER jenkins
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]