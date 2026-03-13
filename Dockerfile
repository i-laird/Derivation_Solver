
# stage 1
FROM maven:3.9.9-amazoncorretto-25-debian-bookworm AS build

LABEL maintainer = "Ian Laird"

WORKDIR /build

COPY pom.xml .

COPY ./src ./src

RUN mvn package -DskipTests

# stage 2
FROM eclipse-temurin:25-jre-alpine AS run

RUN apk add --no-cache curl

# Create a non-root group and user
RUN addgroup -S calculator && adduser -S -G calculator calculator

WORKDIR /run

COPY --from=build /build/target/DerivationSolver-*.jar ./app.jar

# Change ownership of the jar file and workdir
RUN chown -R calculator:calculator /run

USER calculator

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

CMD ["java", "-jar", "./app.jar"]