FROM openjdk:17
ADD /target/security-0.0.1-SNAPSHOT.jar backend.jar
LABEL authors="Asakura"
ENTRYPOINT ["java", "-jar", "backend.jar"]