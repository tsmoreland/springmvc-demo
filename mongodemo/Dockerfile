FROM maven:3.8.1-openjdk-16-slim as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/
RUN mvn clean package
COPY target/mongodemo-${VERSION}.jar target/application.jar

FROM mcr.microsoft.com/java/jre:16u0-zulu-ubuntu
WORKDIR /app/
COPY --from=BUILDER /build/target/application.jar /app/
CMD java -jar /app/application.jar --spring.profiles.active=docker
