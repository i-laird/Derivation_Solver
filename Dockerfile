
# stage 1
FROM maven:3.9.1-amazoncorretto-20-debian-bullseye as build

LABEL maintainer = "Ian Laird"

WORKDIR /build

COPY pom.xml .

COPY ./src ./src

RUN mvn package

# stage 2
FROM openjdk:20-jdk as run

WORKDIR /run

COPY --from=build /build/target/DerivationSolver-*.jar ./

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

CMD ["java", "-jar", "./DerivationSolver-2.1-SNAPSHOT.jar"]
