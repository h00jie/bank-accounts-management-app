# Beneficiaries Accounts Management Application

This is a Spring Boot application for managing beneficiaries' accounts. The project demonstrates the following:

- Java Spring Boot setup
- Building and running the application using Maven
- Containerizing the application using Docker

## Prerequisites

Before running the application, ensure you have the following installed:

- Java (JDK 11 or higher)
- Maven
- Docker
- Git

## Clone the repository

To get started, clone the repository from GitHub:

```bash
git clone https://github.com/h00jie/bank-accounts-management-app.git
cd bank-accounts-management-app
mvn clean install
mvn package
docker build -t beneficiaries-accounts-management-app .
docker run -p 8080:8080 beneficiaries-accounts-management-app

```

## Test some endopoints

```bash

curl -X 'GET' \
'http://localhost:8080/beneficiaries/835' \
-H 'accept: application/json'


curl -X 'GET' \
  'http://localhost:8080/beneficiaries/835/largest-withdrawal' \
  -H 'accept: */*'

```

