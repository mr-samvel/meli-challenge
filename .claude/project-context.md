# Project Context: MercadoLibre Technical Challenge

## Overview
Backend API that provides all necessary data to support an item detail page, inspired by MercadoLibre. This is a technical challenge focused exclusively on backend design and implementation following enterprise best practices.
This is a backend API system designed to support a product detail page similar to MercadoLibre. The system focuses on demonstrating enterprise-grade backend development practices including proper error handling, comprehensive testing, and API documentation.

## Tech Stack
- **Language**: Java 21
- **Framework**: Spring Boot 3.5.6
- **Build Tool**: Gradle 8.14.3 (Groovy)
- **API Documentation**: SpringDoc OpenAPI 3.0 (Swagger UI)
- **Testing**: JUnit 5, Mockito, AssertJ

## Architecture Pattern
Layered architecture following Spring Boot best practices:
```
Controller (REST API) → Service (Business Logic) → Repository (Data Access) → JSON Files
```

**Layered Architecture** with clear separation of concerns:

```
┌─────────────────────────────────────────────┐
│         REST API Layer (Controllers)        │
│  - HTTP endpoints                           │
│  - Request/Response DTOs                    │
│  - Input validation                         │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────▼───────────────────────────┐
│       Business Logic Layer (Services)       │
│  - Business rules                           │
│  - Data transformation                      │
│  - Orchestration                            │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────▼───────────────────────────┐
│      Data Access Layer (Repositories)       │
│  - JSON file operations                     │
│  - Data mapping                             │
│  - Query logic                              │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────▼───────────────────────────┐
│           Data Storage (JSON Files)         │
│  - Simulated relational database            │
│  - File-based persistence                   │
└─────────────────────────────────────────────┘
```

## Package Structure
```
br.com.meli.technicalchallenge/
├── config/         - Spring configuration classes (OpenAPI, CORS, etc.)
├── controller/     - REST API endpoints (@RestController)
├── service/        - Business logic (@Service)
├── repository/     - Data access layer (@Component)
├── model/
│   ├── domain/     - Domain entities (internal representation)
│   └── dto/        - Data Transfer Objects (API request/response)
└── exception/      - Custom exceptions and global error handler

src/main/resources/
├── data/           - JSON files simulating relational database tables
└── application.yml - Application configuration

src/test/java/br/com/meli/technicalchallenge/
├── unit/           - Fast, isolated unit tests
└── integration/    - Integration tests (full API request/response)
```

## Data Storage Approach
- **No real database**: Uses JSON files to persist data
- **Simulates relational database**: JSON files represent database tables with relations via IDs
- **Location**: `src/main/resources/data/*.json`
- **Access**: Read-only or read/write depending on requirements
- **Strategy**: Repository layer abstracts data access, making it easy to swap to real DB later

## Coding Conventions

### Java Style
- Use Java 21 features (records for simple DTOs, pattern matching, etc.)
- Prefer immutability (final fields, immutable collections)
- Use Lombok annotations (@Data, @Builder, @AllArgsConstructor) to reduce boilerplate
- Follow standard Java naming conventions (camelCase for methods/variables, PascalCase for classes)

### Spring Boot Patterns
- **Controllers**: Use `@RestController`, keep thin (delegate to services)
- **Services**: Use `@Service`, contain business logic
- **Repositories**: Use `@Component` or `@Repository`, handle data access
- **DTOs**: Separate request/response DTOs from domain models
- **Validation**: Use Jakarta Validation annotations (`@Valid`, `@NotNull`, etc.)

### Error Handling
- Create custom exceptions in `exception/` package as needed
- Use `@ControllerAdvice` with `@ExceptionHandler` for global error handling
- Return consistent error response format (HTTP status, message, timestamp)
- Include proper HTTP status codes (404, 400, 500, etc.)

### API Design
- RESTful principles (proper HTTP methods and status codes)
- Consistent naming conventions (kebab-case for URLs)
- Comprehensive OpenAPI documentation with annotations

## Testing Strategy

### Unit Tests (`src/test/java/.../unit/`)
- Test individual components in isolation
- Mock dependencies using Mockito
- Fast execution, no Spring context
- Use JUnit 5 assertions or AssertJ for readability
- Focus on business logic, edge cases, validation

### Integration Tests (`src/test/java/.../integration/`)
- Test full request/response cycle
- Use `@SpringBootTest` and `@AutoConfigureMockMvc`
- Verify controller-service-repository integration
- Test API contracts, status codes, response formats
- May use test data fixtures

### Test Naming
- Descriptive test method names: `shouldReturnProductWhenValidIdProvided()`
- Given-When-Then structure in test body

## Dependencies Management
All dependencies managed in `build.gradle`:
- Spring Boot starters (web, validation, devtools)
- Jackson for JSON
- Lombok for reducing boilerplate
- SpringDoc OpenAPI for API documentation
- Testing libraries (Mockito, AssertJ, JUnit 5)

## Development Workflow
1. **Define API contract** - Design endpoint, request/response DTOs
2. **Create domain models** - Internal data representation
3. **Implement repository** - JSON file data access
4. **Implement service** - Business logic
5. **Implement controller** - REST endpoint
6. **Add validation** - Request validation, error handling
7. **Document API** - OpenAPI annotations
8. **Write tests** - Unit tests first, then integration tests
9. **Verify** - Test manually via Swagger UI

## Key Principles
- **Separation of concerns**: Each layer has clear responsibility
- **Testability**: Write testable code, mock dependencies
- **Documentation**: API documentation via OpenAPI/Swagger
- **Error handling**: Graceful error handling with proper status codes
- **Clean code**: Readable, maintainable, following conventions
- **Type safety**: Leverage Java's type system
- **Validation**: Validate inputs at API boundary

## API Access
- **Base URL**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## Build & Run Commands
```bash
# Build project
./gradlew clean build

# Run application
./gradlew bootRun

# Run tests
./gradlew test

# Run specific test
./gradlew test --tests "ClassName.methodName"
```

## Notes for AI Assistants
- Follow Spring Boot and Java best practices
- Keep controllers thin, services focused, repositories simple
- Write clean, idiomatic Java code
- Use Lombok to reduce boilerplate (getters, setters, constructors)
- Add proper error handling and validation
- Include OpenAPI documentation annotations
- Write comprehensive tests (unit + integration)
- Use Mockito for mocking
- Use JUnit 5 or AssertJ assertions for better readability
- Avoid creating files until data schema is defined

## Development Guidelines

1. **Start with contract**: Define API endpoint first
2. **Work outside-in**: Controller → Service → Repository
3. **Test as you go**: Write tests alongside code
4. **Document APIs**: Add OpenAPI annotations
5. **Handle errors**: Consider failure scenarios
6. **Keep it simple**: Don't over-engineer
7. **Follow conventions**: Spring Boot best practices

### RESTful Principles
- Use appropriate HTTP methods (GET, POST, PUT, DELETE)
- Resource-based URLs (e.g., `/api/products/{id}`)
- Proper status codes (200, 201, 404, 400, 500)
- Stateless operations


## Component Responsibilities

### Controllers
- Handle HTTP requests and responses
- Validate incoming data (using Jakarta Validation)
- Transform DTOs to domain models (and vice versa)
- Delegate business logic to services
- Handle HTTP status codes
- **Do NOT** contain business logic

### Services
- Implement business logic and rules
- Services are domain-specific (instead of entity-specific)
- Know of repositories in its domain
- Call other Services if it needs to integrate with its domain
- Orchestrate multiple repository calls if needed
- Transform data between domain models
- Throw domain-specific exceptions
- **Do NOT** know about HTTP or DTOs

### Repositories
- Read/write data from JSON files
- Map JSON to domain objects
- Provide query capabilities
- Handle I/O exceptions
- **Do NOT** contain business logic

### Models
- **Domain Models** (`model/domain/`): Internal representation, business entities
- **DTOs** (`model/dto/`): External API contracts (request/response)
- Keep domain models separate from API contracts

### Exception Handling
- Custom exceptions for different error scenarios
- Global exception handler (`@ControllerAdvice`)
- Consistent error response format
- Proper HTTP status codes mapping

## Data Storage Strategy

### JSON File Structure
Each JSON file represents a database table:
```
data/
├── products.json       - Main product information
├── categories.json     - Product categories
├── sellers.json        - Seller information
├── reviews.json        - Product reviews
└── ...
```

### Relationships
- Use IDs to establish relationships (e.g., `product_id`, `seller_id`)
- Repository layer handles "joins" by reading multiple files
- Service layer assembles complete objects

### File Access Pattern
1. Load JSON file using Spring's `ResourceLoader`
2. Deserialize using Jackson
3. Filter/transform data in memory
4. Return domain objects

## Design Decisions

### Test Pyramid
```
        ┌──────────────┐
        │ Integration  │  ← Fewer, test full API flow
        ├──────────────┤
        │     Unit     │  ← More, test individual components
        └──────────────┘
```

### Unit Tests
- **Target**: Services, Repositories, Utilities
- **Scope**: Single component in isolation
- **Mocking**: Mock all dependencies
- **Speed**: Fast (no Spring context)
- **Tools**: JUnit 5, Mockito, AssertJ

### Integration Tests
- **Target**: Controllers (full API endpoints)
- **Scope**: Request → Controller → Service → Repository → Response
- **Context**: Full Spring Boot context
- **Speed**: Slower (loads Spring)
- **Tools**: `@SpringBootTest`, `MockMvc`, JUnit 5
