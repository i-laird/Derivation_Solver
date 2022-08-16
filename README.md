# Derivation Solver

This is a RESTful derivation solver for single variable equations. This is one of my personal projects and has been in development on and off for three years now. If you encounter an error please report it to me and I will try and fix it as soon as possible.

Interaction with the calculator happens through a REST API.

## Built With
* Spring Boot
* Maven
* Java 12
* Docker

# How To Run

Clone this repository to your local machine

`git clone <repo url>`

## Maven
`mvn spring-boot:run`

## Docker
To initialize the MySQL database:
```
docker run --name mysql2 -p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=1234 \
-e MYSQL_USER=api \      
-e MYSQL_PASSWORD=1234 \
-e MYSQL_DATABASE=calculator \
-d mysql/mysql-server  
```
To build the Docker container
`docker build -t derivation_solver .`
To run the docker container
`docker run -p 8080:8080 -d derivation_solver`

## Author
[Ian Laird](https://www.linkedin.com/in/ian-laird-b9846198/)