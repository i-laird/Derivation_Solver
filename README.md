# Derivation Solver

![CodeQL](https://github.com/i-laird/Derivation_Solver/actions/workflows/codeql.yml/badge.svg)
![Junit](https://github.com/i-laird/Derivation_Solver/actions/workflows/junit.yml/badge.svg)
![Linting](https://github.com/i-laird/Derivation_Solver/actions/workflows/linter.yml/badge.svg)

This is a RESTful derivation solver for single variable equations. This is one of my personal projects and has been in
development on and off for three years now. If you encounter an error please report it to me and I will try and fix it
as soon as possible.

Interaction with the calculator happens through a REST API.

## Contributing Guidelines

If you would like to contribute, please assign an issue to yourself (create issue if necessary). This allows work
to not be duplicated. Also please follow the following guidelines:

- Only merge to main with a pull-requests.
- Make `i-laird` the reviewer on pull-requests.
- You must use squash merge.
- Do not merge if automated checks are not passing.

## New User?

Before contributing to this project there are a few configuration steps you should follow in order to properly
configure your machine.

### Install List

+ Maven
+ Java 21 (Amazon Corretto)
+ Docker

## GitHub Actions

Current Actions are Described [here](actions.md)

Automated Testing is currently performed on the repository using [GitHub Actions](https://github.com/features/actions).
There are plans to deploy the application automatically to the cloud using GitHub Actions.

Actions are defined in the `.github/workflows` folder. These tests are required to pass in
order to merge a pull request into main.

Note: the only approved way to contribute to main is by
a [squash merge](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/incorporating-changes-from-a-pull-request/about-pull-request-merges#squash-and-merge-your-commits).

------------------------------------------------------------------------

## Run Steps

## Make sure Docker is Running

If you have not already installed [Docker](https://www.docker.com/products/docker-desktop/), do so now and make sure it
is running.

## MYSQL Database

This application requires a MYSQL database to be running on port 3306 in order to start. While there are many ways to
do this here is how you would do this with Docker.

### Download Docker Image

```shell
sudo docker pull mysql/mysql-server:latest
```

### Verify you now have the image locally

```shell
docker images | grep "mysql/mysql-server"
```

### Creating DB Container

```shell
docker run \
--name mysql2 \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=1234 \
-e MYSQL_USER=api \
-e MYSQL_PASSWORD=1234 \
-e MYSQL_DATABASE=calculator \
-d mysql/mysql-server
```

Note: from a security perspective this is not good because of the
hardcoded username and password. Be sure to change the password using shell. Leaving like this for now because
it is convenient for testing.

### Starting Shell(s)

#### Bash Shell Within Container

Find the container ID of the mysql container.

```shell
docker ps | grep "mysql2"
```

Starting Bash shell in the MYSQL container

```shell
docker exec -it <container_id> bash
```

#### MYSQL shell

```shell
mysql -h localhost -u root -p
```

## Running Locally

There are two ways to run the application locally. The first way is using the Spring Maven plugin and the second is by spinning
up a Docker container. Both require a MySQL database running on port 3306 (see above).

> **Note:** The production deployment on AWS App Runner currently runs without a database. The `/register` and `/authenticate`
> endpoints are unavailable in production. Only the `/derivative` and `/expression` endpoints are active.

### Spring Maven Plugin

```shell
mvn spring-boot:run
```

### Docker Container

To build the Docker container

```shell
docker build --platform linux/amd64 -t derivation-solver .
```

To run the Docker container

```shell
docker run -p 8080:8080 -d derivation-solver
```

## Deploying to AWS App Runner

### Prerequisites
- AWS CLI installed and configured (`aws configure`)
- Docker installed and running
- An ECR repository created for this project

### Build and Push a New Image

```shell
# Authenticate Docker with ECR
aws ecr get-login-password --region us-east-1 | \
  docker login --username AWS --password-stdin \
  <your-account-id>.dkr.ecr.us-east-1.amazonaws.com

# Build for the correct platform (required on Apple Silicon)
docker build --platform linux/amd64 -t derivation-solver .

# Tag and push
docker tag derivation-solver:latest \
  <your-account-id>.dkr.ecr.us-east-1.amazonaws.com/derivation-solver:latest

docker push \
  <your-account-id>.dkr.ecr.us-east-1.amazonaws.com/derivation-solver:latest
```

App Runner will automatically redeploy once the new image is pushed.

### Required Environment Variables

The following environment variables must be set on the App Runner service:

| Variable                 | Description                                       |
|--------------------------|---------------------------------------------------|
| `SPRING_PROFILES_ACTIVE` | Set to `prod`                                     |
| `JWT_SECRET`             | Base64-encoded secret key for signing JWT tokens  |

Generate a JWT secret with:
```shell
openssl rand -base64 32
```

### Manual Redeploy (if auto-deploy is disabled)

```shell
aws apprunner start-deployment \
  --service-arn <your-service-arn> \
  --region us-east-1
```

## PostMan

Download Postman to send test API calls to the service. Open the workspace
[here](https://dark-capsule-103483.postman.co/workspace/Team-Workspace~113860f3-c1f0-458b-87eb-65444adb8f74/collection/5427748-fde05af8-b065-4de5-b6d4-39b1dc0a9a79?action=share&creator=5427748).

## Linting

Proper code format is super important! There is a GitHub action that will fail if the code is not formatted
properly.

You can test Super Linter with the following command.

```shell
docker run \
  -e ACTIONS_RUNNER_DEBUG=true \
  -e RUN_LOCAL=true \
  -v /path/to/local/codebase:/tmp/lint \
  ghcr.io/super-linter/super-linter:latest
```

You can have IntelliJ automatically enforce Google Java Style by following these
[instructions](https://github.com/google/google-java-format/blob/master/README.md#intellij-jre-config).

## Author

[Ian Laird](https://www.linkedin.com/in/ian-laird-b9846198/)

## Debugging

If you attempt to connect a Schema that does not match the JPA generated schema you will get a bug that looks something
like this `Error creating bean with name 'userRepository'`.

Easiest way to fix this is not delete the old schema and let JPA recreate it for you (this only works if you do not care
about any information in your db).
If not you will need to do a schema migration. If you do this please update the documentation.
