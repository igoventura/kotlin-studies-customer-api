# Customer API - A Reactive Spring Boot & Kotlin Project

This project is a fully reactive REST API for managing customers, built with Spring Boot, Spring WebFlux, and Kotlin Coroutines. It serves as a comprehensive example of building modern, non-blocking web services in the Spring ecosystem.

The development of this project followed a guided, step-by-step process, evolving from a traditional blocking Spring MVC application into a fully asynchronous, reactive service. This evolution demonstrates the key architectural changes required for building scalable, high-performance applications.

## Features

- **Full CRUD Operations**: Create, Read, Update, and Delete functionality for customers.
- **Asynchronous & Non-Blocking**: Built on a fully reactive stack (WebFlux, R2DBC, Coroutines) to handle high concurrency with minimal resources.
- **Paginated Data Fetching**: The `GET /api/v1/customers` endpoint supports efficient pagination.
- **Input Validation**: Robust validation on request bodies to ensure data integrity.
- **Centralized Exception Handling**: Graceful error handling for issues like not-found resources and validation failures.
- **API Documentation**: Integrated Swagger UI for live API documentation and testing.
- **Clean Architecture**: Follows a standard layered architecture (Controller, Service, Repository) with a clear separation of concerns.

## Tech Stack

- **Framework**: Spring Boot 3
- **Language**: Kotlin
- **Asynchronous Programming**: Kotlin Coroutines
- **Web Layer**: Spring WebFlux (Reactive)
- **Database Access**: Spring Data R2DBC (Reactive)
- **Database**: H2 (In-Memory)
- **API Documentation**: springdoc-openapi (Swagger UI)
- **Security**: Spring Security (Reactive)
- **Build Tool**: Gradle with Kotlin DSL

## Prerequisites

- JDK 21 or newer
- Gradle 8.x

## How to Run

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/igoventura/kotlin-studies-customer-api/
    cd kotlin-studies-customer-api
    ```

2.  **Build the project using the Gradle wrapper:**
    ```bash
    ./gradlew build
    ```

3.  **Run the application:**
    ```bash
    ./gradlew bootRun
    ```

The application will start on `http://localhost:8080`.

## Accessing the API

- **API Base URL**: `http://localhost:8080/api/v1`
- **Swagger UI (Live Documentation)**: `http://localhost:8080/swagger-ui.html`
- **H2 Database Console**: `http://localhost:8080/h2-console`
    - **JDBC URL**: `r2dbc:h2:mem:///testdb` (Note: Use the R2DBC URL, but the console itself is JDBC-based).

## API Endpoints

| Method | URL                               | Description                                                               |
| :----- | :-------------------------------- | :------------------------------------------------------------------------ |
| `POST` | `/api/v1/customers`               | Creates a new customer.                                                   |
| `GET`  | `/api/v1/customers`               | Retrieves a paginated list of all customers. Supports `?page` and `?size`. |
| `GET`  | `/api/v1/customers/{id}`          | Retrieves a single customer by their unique ID.                           |
| `PUT`  | `/api/v1/customers/{id}`          | Updates an existing customer's details.                                   |
| `DELETE`| `/api/v1/customers/{id}`          | Deletes a customer by their unique ID.                                    |

---

## Project Evolution: From Blocking to Reactive

This project was intentionally built in two major phases to demonstrate the migration from a traditional architecture to a modern, reactive one.

### Phase 1: The Blocking (MVC) Architecture

The initial version was built using a standard Spring Boot stack:
- **Spring Web MVC**: For handling requests with a thread-per-request model.
- **Spring Data JPA**: For blocking database access.

This architecture is simple and effective for many use cases but can become a bottleneck under high I/O load as threads get blocked waiting for database operations.

### Phase 2: The Reactive (WebFlux) Migration

The application was then refactored into a fully non-blocking service. This involved several key changes:
1.  **Web Layer**: `spring-boot-starter-web` was replaced with `spring-boot-starter-webflux`.
2.  **Persistence Layer**: `spring-boot-starter-data-jpa` was replaced with `spring-boot-starter-data-r2dbc`.
3.  **Code Refactoring**: All functions in the controller, service, and repository layers were converted to `suspend` functions, leveraging Kotlin Coroutines for asynchronous operations that suspend instead of blocking.
4.  **Configuration Updates**: `SecurityConfig` and `springdoc-openapi` dependencies were updated to their reactive-compatible versions.
5.  **Schema Management**: A `schema.sql` file was added, as R2DBC does not provide automatic schema generation like JPA.

This migration resulted in a more scalable and resource-efficient application, capable of handling a larger number of concurrent requests with a small, fixed number of threads.

---

## License

This project is licensed under the MIT License.

**MIT License**

Copyright (c) 2025 Igo S. Ventura

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.