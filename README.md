# Beneficiaries Accounts Management Application

This is a Spring Boot application for managing beneficiaries' accounts. The project demonstrates the following:

- Java Spring Boot setup
- Building and running the application using Maven
- Containerizing the application using Docker

## Prerequisites

Before running the application, ensure you have the following installed:

- Java (JDK 17 or higher)
- Maven
- Docker
- Git

## Clone the repository

To get started, clone the repository from GitHub and then start the app via docker

```bash
git clone https://github.com/h00jie/bank-accounts-management-app.git
cd bank-accounts-management-app
```

## Start the app 
We can  do it in 2 ways via maven or docker 

### Maven 
```bash
mvn spring-boot:run
```
### docker:
```bash
docker-compose up --build
```
## Visit OpenAPI URL
[Swagger URL](http://localhost:8080/swagger-ui/index.html)

## Test some endopoints

```bash

curl -X 'GET' \
'http://localhost:8080/beneficiaries/835' \
-H 'accept: application/json'


curl -X 'GET' \
  'http://localhost:8080/beneficiaries/835/largest-withdrawal' \
  -H 'accept: */*'

```

