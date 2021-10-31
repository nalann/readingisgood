FROM maven:3.8.1-openjdk-11 AS JAR_SOURCE
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package

FROM openjdk:11
COPY --from=JAR_SOURCE /build/target/getir-0.0.1-SNAPSHOT.jar /build/target/
VOLUME /main-app
WORKDIR /build/target/
ENTRYPOINT ["java","-jar","getir-0.0.1-SNAPSHOT.jar"]