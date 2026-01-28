# elearning-backend

Backend microservices for an **E-Learning platform**, built with **Java & Spring Boot**.

This repository contains multiple services following a microservice architecture, including authentication, user management, gateway routing, and shared SDK modules.

---

## Overview

Main services/modules in this project:

* **gateway** – API Gateway (routing, filters, authentication)
* **discovery** – Service Discovery (Eureka)
* **auth** – Authentication service (JWT issuing & validation)
* **idp** – Identity Provider (accounts, profiles)
* **user** – User management service
* **config** – (Optional) Spring Cloud Config Server
* **elearning-sdk** – Shared library (DTOs, common utilities, clients)

---

## Tech Stack

* Java 17+
* Spring Boot
* Spring WebFlux (Reactive, Mono/Flux)
* Spring Cloud (Gateway, Discovery, Config)
* MongoDB
* Docker / Docker Compose
* Redis

---

## Project Structure

```
.
├─ auth
├─ config
├─ discovery
├─ elearning-sdk
├─ gateway
├─ idp
├─ user
└─ pom.xml
```

Each folder is an independent Spring Boot application (except `elearning-sdk`).

---

## Prerequisites

Make sure you have installed:

* **Java JDK 17+**
* **Maven 3.6+**
* **MongoDB** (local or Atlas)
* **Docker & Docker Compose** (optional, recommended)

---

## Environment Variables (Example)

Common variables:

* `SPRING_PROFILES_ACTIVE` – Active profile (`dev`, `prod`)
* `MONGODB_URI` – MongoDB connection string
* `JWT_SECRET` – Secret key for JWT signing
* `SERVER_PORT` – Service port

---

## Build the Project

From the root directory:

```bash
mvn clean package -DskipTests
```

---

## Run Services Locally

Each module can be started independently.

### Example: run Gateway service

```bash
cd gateway
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Recommended startup order

1. `discovery`
2. `config` (if used)
3. `auth`, `idp`, `user`
4. `gateway`

Check each module’s `application.yml` for the default port.

---

## Docker Compose (Sample)

Example `docker-compose.yml` for MongoDB + Discovery service:

```yaml
version: '3.8'
services:
  mongo:
    image: mongo:6
    container_name: elearning-mongo
    restart: unless-stopped
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: example
    ports:
      - "27017:27017"
    volumes:
      - mongo_data:/data/db

  discovery:
    image: openjdk:17-jdk
    container_name: elearning-discovery
    working_dir: /app
    volumes:
      - ./discovery:/app
    command: ["sh", "-c", "mvn spring-boot:run"]
    ports:
      - "8761:8761"

volumes:
  mongo_data:
```

> This is a **sample only**. For production, create Docker images for each service.

---

## API Testing

Use **Postman** or **curl** to test APIs.

Example login request:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user@example.com","password":"password"}'
```

---

## Development Notes

* Shared DTOs and utilities should be placed in `elearning-sdk`
* Gateway handles authentication & routing
* Services communicate via REST (and possibly service discovery)

---

## Contributing

1. Fork this repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit your changes
4. Push to your fork
5. Open a Pull Request

---

## Future Improvements

* Add OpenAPI / Swagger documentation
* Add CI/CD pipeline
* Add centralized logging & monitoring
* Add Docker images for all services
* Add architecture diagram

---

## Author

**DamThanhLam**
GitHub: [https://github.com/DamThanhLam](https://github.com/DamThanhLam)

---

If you want, I can:

* Customize this README based on your actual `application.yml`
* Add Swagger/OpenAPI documentation section
* Create a production-ready `docker-compose.yml`
* Write a system architecture diagram description
