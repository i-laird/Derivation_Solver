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
+ Java 20 (Amazon Corretto)
+ Docker

## GitHub Actions
Current Actions are Described [here](actions.md)

Automated Testing is currently performed on the repository using [GitHub Actions](https://github.com/features/actions).
There are plans to deploy the application automatically to the cloud using GitHub Actions.

Actions are defined in the `.github/workflows` folder. These tests are required to pass in
order to merge a pull request into main.

Note: the only approved way to contribute to main is by a [squash merge](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/incorporating-changes-from-a-pull-request/about-pull-request-merges#squash-and-merge-your-commits).

------------------------------------------------------------------------

## Run Steps

## Make sure Docker is Running

If you have not already installed [Docker](https://www.docker.com/products/docker-desktop/), do so now and make sure it is running.

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

## Running Application
There are two ways to run the application. The first way is using the spring maven plugin and the second is by spinning up a docker container.

### Spring Maven Plugin

```shell
mvn spring-boot:run
```

## Docker Container

To build the Docker container

```shell
docker build -t derivation_solver .
```

To run the docker container

```shell
docker run -p 8080:8080 -d derivation_solver
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
If you attempt to connect a Schema that does not match the JPA generated schema you will get a bug that looks something like this `Error creating bean with name 'userRepository'`.

Easiest way to fix this is not delete the old schema and let JPA recreate it for you (this only works if you do not care about any information in your db).
If not you will need to do a schema migration. If you do this please update the documentation.
