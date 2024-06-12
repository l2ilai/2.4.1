FROM maven:alpine as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
ADD . $HOME
RUN mvn package

FROM openjdk:11-jdk-slim
COPY --from=build /usr/app/target/security-0.0.1-SNAPSHOT.jar /app/runner.jar
ENTRYPOINT java -jar /app/runner.jar

#FROM openjdk:17
#ADD /security-0.0.1-SNAPSHOT.jar backend.jar
#LABEL authors="Asakura"
#ENTRYPOINT ["java", "-jar", "backend.jar"]
#
#FROM maven:3.6.3-jdk-11-slim AS build
#RUN mkdir -p /workspace
#WORKDIR /workspace
#COPY pom.xml /workspace
#COPY src /workspace/src
#RUN mvn -B -f pom.xml clean package -DskipTests
#
#FROM openjdk:11-jdk-slim
#COPY --from=build /workspace/target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]