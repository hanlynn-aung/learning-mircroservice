# Banking Microservices Platform

## 🏦 Overview
A modern banking platform built with Spring Boot 4.0.1 and Java 21, following microservices architecture. This project consists of three core services: Accounts, Loan, and Card management, along with centralized configuration using Spring Cloud Config Server and asynchronous messaging via RabbitMQ.

## 🚀 Services

| Service                | Port | Description |
|------------------------|------|-------------|
| Accounts              | 8080 | Customer account management |
| Loan                  | 8090 | Loan processing system |
| Card                  | 9000 | Card management system |
| Config Server         | 8888 | Centralized configuration |
| RabbitMQ Management   | 15672 | Message broker dashboard |

## 🛠 Tech Stack

### Containerization
- **Docker**
- **Jib** (for Card service)
- **Buildpacks** (for Loan service)
- **Dockerfile** (for Accounts service)
- **Docker Compose** (for local development)

### Core Technologies
- **Java 21**
- **Spring Boot 4.0.1**
- **Spring Cloud Config** (for centralized configuration)
- **Spring Cloud Bus** (with RabbitMQ)
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **SpringDoc OpenAPI 2.8.5**
- **Lombok**
- **Maven**

### Message Broker
- **RabbitMQ** - For asynchronous communication between services
  - Default port: 5672
  - Management UI: http://localhost:15672 (guest/guest)

### Configuration Management
- **Spring Cloud Config Server**
  - Port: 8888
  - Git-based configuration
  - Automatic refresh with Spring Cloud Bus

## 🚦 Prerequisites

- JDK 21
- Maven 3.9+
- Git
- Docker and Docker Compose

## 🏃‍♂️ Quick Start

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd microservice-account-project
   ```

2. **Start infrastructure with Docker Compose**
   ```bash
   # Start RabbitMQ and Config Server
   docker-compose up -d
   ```

3. **Run the services**
   ```bash
   # Run Config Server
   cd configserver
   mvn spring-boot:run
   
   # In separate terminals, run each service:
   cd account && mvn spring-boot:run
   cd loan && mvn spring-boot:run
   cd card && mvn spring-boot:run
   ```

4. **Access the services**
   - Accounts: http://localhost:8080
   - Loan: http://localhost:8090
   - Card: http://localhost:9000
   - Config Server: http://localhost:8888
   - RabbitMQ Management: http://localhost:15672 (guest/guest)

## 📚 API Documentation

Each service includes interactive API documentation:
- **Swagger UI**: `http://localhost:<port>/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:<port>/v3/api-docs`

## 🔄 Configuration Management

### Config Server
- Centralized configuration for all services
- Configuration files are stored in a Git repository
- Supports profiles (dev, prod, etc.)
- Automatic refresh with Spring Cloud Bus

### Refresh Configuration
To refresh configuration without restarting services:
```bash
# Send refresh event to all services
curl -X POST http://localhost:<port>/actuator/bus-refresh
```

## 🐇 RabbitMQ Commands

### Start RabbitMQ with Docker
```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

### Common RabbitMQ Commands
```bash
# List queues
rabbitmqadmin list queues

# Publish a message
rabbitmqadmin publish exchange=amq.default routing_key=queue_name payload='{"key":"value"}'

# Check service connections
rabbitmqadmin list connections
```

### Monitoring
- Access RabbitMQ Management UI at http://localhost:15672
- Default credentials: guest/guest

## 🔄 Service Communication

### Asynchronous Messaging
- Services communicate asynchronously using RabbitMQ
- Event-driven architecture for better scalability
- Automatic retry and dead-letter queue support for failed messages

## 🐳 Containerization (Advanced)

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

## 🐳 Docker Compose

This project includes Docker Compose configurations for different environments (development, production) to simplify local development and deployment.

### Quick Start

```bash
# Navigate to the docker-compose directory
cd docker-compose/default

# Start all services in detached mode
docker-compose up -d

# View running containers
docker-compose ps

# View logs for all services
docker-compose logs -f

# Stop all services
docker-compose down
```

### Environment Configurations

- **Default**: Basic setup for local development
- **Development**: Enhanced for development with additional debugging tools
- **Production**: Optimized for production use

### Key Features
- All services (Accounts, Card, Loan, Config Server, RabbitMQ) in isolated containers
- Dedicated bridge network `eazybank` for secure inter-service communication
- Resource limits and health checks for reliability
- Environment-specific configuration support

For detailed documentation, see the [docker-compose README](./docker-compose/README.md)
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
