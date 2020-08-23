FROM openjdk:8-jdk-alpine
EXPOSE 8080
RUN mkdir /hamro-audit
ARG JAR_FILE=./build/libs/hamro-audit-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /hamro-audit/hamro-audit-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/hamro-audit/hamro-audit-0.0.1-SNAPSHOT.jar"]

