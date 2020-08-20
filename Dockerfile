FROM openjdk:8-jdk-alpine
ARG JAR_FILE=hamro-audit-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} hamro-audit-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","hamro-audit-0.0.1-SNAPSHOT.jar"]
