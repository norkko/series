FROM openjdk:8-jdk-alpine
MAINTAINER August

ADD target/series-1.0-SNAPSHOT.jar /app.jar
CMD ["java", "-Xmx200m", "-jar", "/app.jar"]

EXPOSE 8081