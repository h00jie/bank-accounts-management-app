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

## Clone the repository and start the app

To get started, clone the repository from GitHub and then start the app via docker

```bash
git clone https://github.com/h00jie/bank-accounts-management-app.git
cd bank-accounts-management-app
docker-compose up --build
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

