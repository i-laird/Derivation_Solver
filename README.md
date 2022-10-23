# Derivation Solver

This is a RESTful derivation solver for single variable equations. This is one of my personal projects and has been in development on and off for three years now. If you encounter an error please report it to me and I will try and fix it as soon as possible.

Interaction with the calculator happens through a REST API.

# New User?

Before contributing to this project there are a few configuration steps you should follow in order to properly configure your machine.

## Install List
+ Maven
+ Java 18 Note: make sure you install OpenJDK not Oracle
+ Docker

## Github Actions
Automated Testing is currently performed on the repository using [Github Actions](https://github.com/features/actions).
There are plans to deploy the application automatically to the cloud using Github Actions.

Actions are defined in the `.github/workflows` folder. These tests are required to pass in
order to merge a pull request into master.

Note: the only approved way to contribute to master is by a [squash merge](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/incorporating-changes-from-a-pull-request/about-pull-request-merges#squash-and-merge-your-commits).

# Run Steps

## Database

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

### Creating DB and Running it (FIRST TIME)
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
hardcoded username and password. Be sure to change the password using shell.

### Starting DB from image
```shell
docker run \
--name mysql2 \
-p 3306:3306 \
-d mysql/mysql-server
```

### Start Shell
Find the container id of the mysql container.
```shell
docker ps | grep "mysql2"
```

Starting Bash shell in the MYSQL container
```shell
docker exec -it <container_id> bash
```

Starting MYSQL shell
```shell
mysql -h localhost -u root -p
```

## Application
There are two ways to run the application. The first way is using the spring maven plugin and the second is by spinning up a docker container.

## spring maven plugin
```
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

## Author
[Ian Laird](https://www.linkedin.com/in/ian-laird-b9846198/)

## Debugging
If you attempt to connect a Schema that does not match the JPA generated schema you will get a bug that looks something like this `Error creating bean with name 'userRepository'`.

Easiest way to fix this is not delete the old schema and let JPA recreate it for you (this only works if you do not care about any information in your db).
If not you will need to do a schema migration. If you do this please update the documentation.