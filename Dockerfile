
# stage 1
FROM maven:3.9.1-amazoncorretto-20-debian-bullseye as build

MAINTAINER Ian Laird

WORKDIR /build

COPY pom.xml .

COPY ./src ./src

RUN mvn package

FROM openjdk:20-jdk as run

WORKDIR /run

COPY --from=build /build/target/DerivationSolver-*.jar ./

EXPOSE 8080

CMD ["java", "-jar", "./DerivationSolver-2.1-SNAPSHOT.jar"]
