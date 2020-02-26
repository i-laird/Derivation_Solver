
# stage 1
FROM maven:3.6.0-jdk-11-slim as build

MAINTAINER Ian Laird

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY ./src ./src

RUN mvn package

# stage 2
FROM openjdk:11-jre-slim as run

WORKDIR /run

COPY --from=build /build/target/DerivationSolver-*.jar ./

CMD ["java", "-jar", "./target/DerivationSolver.jar"]
