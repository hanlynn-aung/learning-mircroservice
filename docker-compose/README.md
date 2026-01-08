# EazyBank Microservices Docker Compose

This directory contains Docker Compose configurations for deploying EazyBank microservices in different environments.

## Directory Structure

```
docker-compose/
├── default/           # Default development configuration
│   ├── docker-compose.yaml   # Main docker-compose file
│   └── common-config.yaml    # Common configuration for services
├── dev/               # Development environment configurations (to be added)
└── prod/              # Production environment configurations (to be added)
```

## Services

The following services are defined in the default configuration:

- **RabbitMQ**: Message broker for inter-service communication
  - Management UI: http://localhost:15672
  - Default credentials: guest/guest
  - Port: 5672 (AMQP), 15672 (Management UI)

- **Config Server**
  - Port: 8071
  - Health check: http://localhost:8071/actuator/health/readiness

- **Account Service**
  - Port: 8080
  - Application name: account

- **Card Service**
  - Port: 9000
  - Application name: card

- **Loan Service**
  - Port: 8090
  - Application name: loan

## Prerequisites

- Docker Engine 20.10.0+
- Docker Compose 2.0.0+

## Getting Started

1. Navigate to the desired environment directory (e.g., `default/`)
2. Start all services:
   ```bash
   docker-compose up -d
   ```
3. To stop all services:
   ```bash
   docker-compose down
   ```

## Environment Variables

Common environment variables used across services:

- `SPRING_APPLICATION_NAME`: Name of the microservice
- `SPRING_PROFILES_ACTIVE`: Active Spring profile (default: default)
- `SPRING_CONFIG_IMPORT`: Config server URL for configuration

## Network

All services are connected to a shared bridge network named `eazybank` for internal communication.

## Resource Limits

Each microservice is configured with a memory limit of 700MB by default. This can be adjusted in the `common-config.yaml` file.

## Monitoring

- **RabbitMQ Management UI**: http://localhost:15672
- **Spring Boot Actuator**: Available on each service's `/actuator` endpoint

## Notes

- The default configuration is for development purposes only.
- For production use, please review and update the configuration in the `prod/` directory.
- Ensure proper security measures (passwords, ports, etc.) are in place for production deployments.
