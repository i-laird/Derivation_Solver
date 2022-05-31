
# stage 1
FROM maven:3.8.5-openjdk-18-slim as build

MAINTAINER Ian Laird

WORKDIR /build

COPY pom.xml .

COPY ./src ./src

RUN mvn package

FROM openjdk:18.0-jdk as run

WORKDIR /run

COPY --from=build /build/target/DerivationSolver-*.jar ./

EXPOSE 8080

CMD ["java", "-jar", "./DerivationSolver-2.0-SNAPSHOT.jar"]
