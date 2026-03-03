
# stage 1
FROM maven:3.9.1-amazoncorretto-20-debian-bullseye as build

LABEL maintainer = "Ian Laird"

WORKDIR /build

COPY pom.xml .

COPY ./src ./src

RUN mvn package

# stage 2
FROM openjdk:20-jdk as run

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Create a non-root group and user
RUN groupadd -r calculator && useradd -r -g calculator calculator

WORKDIR /run

COPY --from=build /build/target/DerivationSolver-*.jar ./

# Change ownership of the jar file and workdir
RUN chown -R calculator:calculator /run

USER calculator

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

CMD ["java", "-jar", "./DerivationSolver-2.1-SNAPSHOT.jar"]
