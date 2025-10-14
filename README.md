# Technical Test Backend - Inditex

> Hexagonal Architecture | Spring Boot 3 | Java 17 | REST API

[![Build](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/juanpimr2/technical-test-backend)
[![Tests](https://img.shields.io/badge/tests-35%20passing-brightgreen)](https://github.com/juanpimr2/technical-test-backend)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)](https://spring.io/projects/spring-boot)

REST API service for querying product prices with priority-based selection logic. Built following Clean Architecture principles with Hexagonal Architecture pattern.

---

## ✨ Features

- ✅ **Price Query by Date**: Get applicable price for a product at a specific date
- ✅ **Priority-Based Selection**: Automatically selects highest priority when multiple prices match
- ✅ **RESTful API**: Clean REST endpoints with proper HTTP status codes
- ✅ **Swagger Documentation**: Interactive API documentation with OpenAPI 3.0
- ✅ **Comprehensive Testing**: 35+ tests (unit, integration, E2E with Postman)
- ✅ **Clean Architecture**: Framework-independent domain layer with SOLID principles

---

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.2.0 |
| **Database** | H2 (in-memory) |
| **ORM** | Spring Data JPA |
| **Mapping** | MapStruct 1.5.5 |
| **API Docs** | SpringDoc OpenAPI 3 |
| **Testing** | JUnit 5, Mockito, Spring Test |
| **Build** | Maven |

---

## 🚀 Quick Start

### Prerequisites
- JDK 17 or higher
- Maven 3.8+

### Installation & Run
```bash
# Clone repository
git clone https://github.com/juanpimr2/technical-test-backend.git
cd technical-test-backend

# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

### Access Points
- **API Endpoint**: http://localhost:8080/api/prices
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console

---

## 📚 API Documentation

### Endpoint: Get Applicable Price

**`GET /api/prices`**

#### Query Parameters
| Parameter | Type | Required | Description | Example |
|-----------|------|----------|-------------|---------|
| `applicationDate` | DateTime | Yes | ISO 8601 format | `2020-06-14T16:00:00` |
| `productId` | Long | Yes | Product identifier | `35455` |
| `brandId` | Long | Yes | Brand ID (1=ZARA) | `1` |

#### Example Request
```bash
curl "http://localhost:8080/api/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1"
```

#### Example Response (200 OK)
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45,
  "currency": "EUR"
}
```

#### Response Codes
- **200 OK**: Price found successfully
- **404 Not Found**: No price found for given parameters
- **400 Bad Request**: Invalid parameters

---

## 🧪 Testing

### Run All Tests
```bash
# Execute all 35 tests
mvn test

# Run specific test class
mvn test -Dtest=PriceControllerTest
```

### Test Coverage
- **10** Unit tests (Domain layer)
- **7** Unit tests (Application layer with mocks)
- **8** Integration tests (Repository layer)
- **7** Integration tests (End-to-end)
- **3** Additional tests (Context, Cache)

**Total: 35 tests | 100% passing ✅**

### Postman Collection
End-to-end API testing with automated assertions:

1. Import collection from `postman/Technical-Test-Inditex.postman_collection.json`
2. Import environment from `postman/Technical-Test-Local.postman_environment.json`
3. Select "Technical Test - Local" environment
4. Run collection (6 requests, 22 automated assertions)

**Expected Results:**
- ✅ Test 1 (Day 14 at 10:00): Price 35.50 EUR
- ✅ Test 2 (Day 14 at 16:00): Price 25.45 EUR (priority 1)
- ✅ Test 3 (Day 14 at 21:00): Price 35.50 EUR
- ✅ Test 4 (Day 15 at 10:00): Price 30.50 EUR (priority 1)
- ✅ Test 5 (Day 16 at 21:00): Price 38.95 EUR (priority 1)
- ✅ Error Case: 404 Not Found

---

## 🏗️ Architecture

This project follows **Hexagonal Architecture** (Ports & Adapters) with strict separation of concerns:
```
┌─────────────────────────────────────────────────────┐
│            Infrastructure Layer                     │
│  ┌──────────────┐  ┌────────────┐  ┌────────────┐ │
│  │ REST API     │  │  Database  │  │   Config   │ │
│  │ (Controller) │  │ (JPA/H2)   │  │  (Spring)  │ │
│  └──────────────┘  └────────────┘  └────────────┘ │
└────────────────────┬────────────────────────────────┘
                     │ Ports (Interfaces)
┌────────────────────┴────────────────────────────────┐
│            Application Layer                        │
│  ┌──────────────────────────────────────────────┐  │
│  │  Use Cases (Business Logic)                  │  │
│  │  • GetApplicablePriceUseCase                 │  │
│  │  • DTOs for data transfer                    │  │
│  └──────────────────────────────────────────────┘  │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────┴────────────────────────────────┐
│              Domain Layer                           │
│  ┌──────────┐  ┌───────────────────────────────┐   │
│  │ Entities │  │ Repository Ports (Interfaces) │   │
│  │  • Price │  │  • PriceRepositoryPort        │   │
│  └──────────┘  └───────────────────────────────┘   │
│       Framework-Free | Pure Java | SOLID            │
└─────────────────────────────────────────────────────┘
```

### Key Architectural Decisions

1. **Domain Layer**: Framework-free, contains business rules and entities
    - No Spring annotations
    - Validation in constructors
    - Business logic methods (e.g., `isApplicableAt`, `hasHigherPriorityThan`)

2. **Application Layer**: Orchestrates use cases
    - No framework dependencies in use case classes
    - DTOs for external communication
    - Input validation with `Objects.requireNonNull()`

3. **Infrastructure Layer**: Framework-specific implementations
    - REST controllers with Spring annotations
    - JPA entities and repositories
    - MapStruct for entity-domain mapping
    - Spring configuration beans

4. **Dependency Rule**: Dependencies always point inward
    - Infrastructure → Application → Domain
    - Never the reverse

---

## 📁 Project Structure
```
technical-test-backend/
├── src/
│   ├── main/
│   │   ├── java/com/technicaltest/backend/
│   │   │   ├── domain/
│   │   │   │   ├── model/
│   │   │   │   │   └── Price.java              # Domain entity
│   │   │   │   └── port/out/
│   │   │   │       └── PriceRepositoryPort.java # Repository interface
│   │   │   ├── application/
│   │   │   │   ├── dto/
│   │   │   │   │   └── PriceResponseDto.java   # Response DTO
│   │   │   │   └── service/
│   │   │   │       └── GetApplicablePriceUseCase.java
│   │   │   └── infrastructure/
│   │   │       ├── api/
│   │   │       │   └── PriceController.java     # REST controller
│   │   │       ├── persistence/
│   │   │       │   ├── entity/
│   │   │       │   │   └── PriceEntity.java     # JPA entity
│   │   │       │   ├── repository/
│   │   │       │   │   └── PriceJpaRepository.java
│   │   │       │   ├── adapter/
│   │   │       │   │   └── PriceRepositoryAdapter.java
│   │   │       │   └── mapper/
│   │   │       │       └── PriceMapper.java     # MapStruct
│   │   │       └── config/
│   │   │           ├── BeanConfiguration.java
│   │   │           └── OpenApiConfig.java
│   │   └── resources/
│   │       ├── application.yml                  # Configuration
│   │       └── data.sql                         # Initial data
│   └── test/                                    # 35+ tests
│       └── java/com/technicaltest/backend/
│           ├── domain/model/                    # Domain tests
│           ├── application/service/             # Use case tests
│           └── infrastructure/                  # Integration tests
├── postman/                                     # API tests
│   ├── Technical-Test-Inditex.postman_collection.json
│   ├── Technical-Test-Local.postman_environment.json
│   └── README.md
├── pom.xml
└── README.md
```

---

## 💡 Design Decisions & Best Practices

### Code Quality
- ✅ **SOLID Principles**: Single responsibility, dependency inversion
- ✅ **Clean Code**: Meaningful names, small methods, clear intent
- ✅ **Immutability**: Domain entities are immutable
- ✅ **Validation**: Constructor validation with `Objects.requireNonNull()`
- ✅ **Functional Style**: Stream API, lambdas, Optional

### Testing Strategy
- ✅ **Given-When-Then**: Clear test structure
- ✅ **Test Pyramid**: More unit tests than integration tests
- ✅ **@DisplayName**: Descriptive test names
- ✅ **Mocking**: Mockito for isolated unit tests
- ✅ **Integration**: @SpringBootTest for end-to-end validation

### Documentation
- ✅ **Swagger/OpenAPI**: Interactive API documentation
- ✅ **Javadoc**: Key classes documented
- ✅ **README**: Comprehensive usage guide
- ✅ **Postman**: Automated E2E tests

---

## 📝 Git Workflow

This project follows **Conventional Commits** specification:
```
<type>: <description>

Types:
  feat:     New feature
  fix:      Bug fix
  test:     Add or update tests
  docs:     Documentation changes
  refactor: Code refactoring
  style:    Code style changes
  chore:    Build/config changes
```

**Example commits:**
```
feat: add REST controller for price queries
test: add integration tests for 5 required cases
docs: add Postman collection with automated tests
```

---

## 👤 Author

**Juan Pablo Mejía**

- GitHub: [@juanpimr2](https://github.com/juanpimr2)
- Repository: [technical-test-backend](https://github.com/juanpimr2/technical-test-backend)

---

## 📄 License

This project was created as a technical test for Inditex.

---
