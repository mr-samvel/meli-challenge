# MercadoLibre Technical Challenge

Backend API that provides necessary data to support an item detail page. Based on the MercadoLibre product details page.

## Tech Stack

- **Java** 21
- **Spring Boot** 3.5.6
- **Gradle** 8.14.3
- **SpringDoc OpenAPI** 2.3.0 (Swagger)

## Prerequisites

- Java 21 or higher

## Setup Instructions

1. **Clone the repository**

2. **Build the project**
   ```bash
   ./gradlew clean build
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

   The application will start on `http://localhost:8080`

## Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

Also, don't forget to check the [documentations folder](docs)!

## Project Structure

```
docs/               # Documentation for architecture design decisions

src/main/java/br/com/meli/technicalchallenge/
├── config/         # Application configuration beans
├── controller/     # REST API endpoints
├── service/        # Business logic
├── repository/     # Data persistence (JSON files)
├── model/          # Domain models and DTOs
└── exception/      # Custom exceptions and error handling

src/main/resources/
├── data/           # Database simulated through JSON files
└── application.yml # Application configuration

src/test/java/      # Unit and integration tests
```

## Running Tests

```bash
./gradlew test
```

## Data Storage

This project uses JSON files to simulate a relational database, stored in `src/main/resources/data/`.