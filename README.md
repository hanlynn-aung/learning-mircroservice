# Banking Microservices Platform

## 🏦 Overview
A modern banking platform built with Spring Boot 4.0.1 and Java 21, following microservices architecture. This project consists of three core services: Accounts, Loan, and Card management.

## 🚀 Services

| Service                | Port | Description |
|------------------------|------|-------------|
| Accounts               | 8080 | Customer account management |
| Loan | 8090 | Loan processing system |
| Card | 9000 | Card management system |

## 🛠 Tech Stack

### Containerization
- **Docker**
- **Jib** (for Card service)
- **Buildpacks** (for Loan service)
- **Dockerfile** (for Accounts service)

### Core Technologies

- **Java 21**
- **Spring Boot 4.0.1**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **SpringDoc OpenAPI 2.8.5**
- **Lombok**
- **Maven**

## 🚦 Prerequisites

- JDK 21
- Maven 3.9+
- Git

## 🏃‍♂️ Quick Start

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd microservice-account-project
   ```

2. **Run a service**
   ```bash
   # Run Accounts Service
   cd account
   mvn spring-boot:run
   ```

3. **Access the services**
   - Accounts: http://localhost:8080
   - Loan: http://localhost:8090
   - Card: http://localhost:9000

## 📚 API Documentation

Each service includes interactive API documentation:
- **Swagger UI**: `http://localhost:<port>/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:<port>/v3/api-docs`

## 🐳 Containerization

### Accounts Service (Dockerfile)
```bash
# Build the JAR
cd account
mvn clean package

# Build Docker image
docker build -t hanlynnaung7/account:v1 .

# Run container
docker run -p 8080:8080 hanlynnaung7/account:v1
```

### Loan Service (Buildpacks)
```bash
# Build and push image using Buildpacks
cd loan
mvn spring-boot:build-image -DskipTests

# Run container
docker run -p 8090:8090 hanlynnaung7/loan:v1
```

### Card Service (Jib)
```bash
# Build and push image using Jib
cd card
mvn compile jib:build -Dimage=hanlynnaung7/card:v1

# Or to build to local Docker daemon
mvn compile jib:dockerBuild -Dimage=hanlynnaung7/card:v1

# Run container
docker run -p 9000:9000 hanlynnaung7/card:v1
```

### Docker Compose (Recommended for Local Development)

To run all services with a single command using Docker Compose:

```bash
# Navigate to the account directory containing docker-compose.yaml
cd account

# Start all services in detached mode
docker-compose up -d

# View running containers
docker-compose ps

# View logs for all services
docker-compose logs -f

# Stop all services
docker-compose down
```

**Features:**
- All services (Accounts, Card, Loan) run in isolated containers
- Dedicated bridge network `eazybank` for inter-service communication
- Memory limits set to 700MB per service
- Ports exposed: 8080 (Accounts), 8090 (Loan), 9000 (Card)

## 🧪 Testing

Run tests for all services:
```bash
mvn test
```

## 🔧 Development

### Project Structure
```
microservice-accounts-project/
├── accounts/         # Accounts microservice
├── loan/             # Loan microservice
└── card/             # Card microservice
```

### Database
- **H2 Console**: http://localhost:<port>/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave empty)

## 📝 Notes
- Each service is independently deployable
- Services use in-memory H2 database by default
- API versioning follows RESTful best practices
