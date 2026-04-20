# moviestesting

Spring Boot project created from [Spring Initializr](https://start.spring.io/) and used only to run automated tests for **movies-app**:

- Target repository: https://github.com/enrikesancheztec/movies-app/

## Purpose

This repository is a **test automation project only**.

- It is not intended to run business APIs.
- It is used to execute Selenium E2E validations against a running `movies-app` web instance.

## Project Properties

- **Group:** `com.kikesoft`
- **Artifact:** `moviestesting`
- **Version:** `0.0.1-SNAPSHOT`
- **Spring Boot:** `4.0.5`
- **Java:** `17`
- **Build tool:** `Maven` (with Maven Wrapper included)
- **Packaging:** `jar`
- **Application name:** `moviestesting`

### Main Dependencies

- `org.springframework.boot:spring-boot-starter`
- `org.springframework.boot:spring-boot-starter-test` (scope `test`)

## Prerequisites

- Java 17 installed and available in `PATH`
- Permissions to run `./mvnw` on macOS/Linux
- `movies-app` web application running and reachable from this machine

## Environment Configuration For E2E Tests

E2E tests resolve the web base URL in this order:

1. JVM property: `-Dweb.base.url=...`
2. Environment variable: `WEB_BASE_URL`
3. Local file: `env.properties` (`web.base.url=...`)
4. Default fallback: `http://localhost:3000`

### Use `env.properties`

1. Copy `env.properties.example` to `env.properties`.
2. Set your target URL.

Example:

```properties
web.base.url=http://localhost:3000
```

Notes:

- `env.properties` is ignored by git.
- `env.properties.example` is tracked as a template.

## Build And Compile Tests

### macOS/Linux

```bash
./mvnw clean test-compile
```

### Windows (PowerShell/CMD)

```powershell
mvnw.cmd clean test-compile
```

## Run Tests

### Run all tests (macOS/Linux)

```bash
./mvnw test
```

### Run all tests (Windows PowerShell/CMD)

```powershell
mvnw.cmd test
```

### Run one test class

macOS/Linux:

```bash
./mvnw -Dtest=ListProducersTest test
```

Windows PowerShell/CMD:

```powershell
mvnw.cmd -Dtest=ListProducersTest test
```

### Run one specific test method

macOS/Linux:

```bash
./mvnw -Dtest=CreateProducersTest#shouldCreateProducerAndShowItInList test
```

Windows PowerShell/CMD:

```powershell
mvnw.cmd -Dtest=CreateProducersTest#shouldCreateProducerAndShowItInList test
```

### Run tests with custom target URL

macOS/Linux:

```bash
./mvnw -Dweb.base.url=http://10.0.0.5:3000 -Dtest=CreateProducersTest test
```

Windows PowerShell/CMD:

```powershell
mvnw.cmd -Dweb.base.url=http://10.0.0.5:3000 -Dtest=CreateProducersTest test
```

## Current E2E Test Areas

- Producers list page validations
- Producer details link validations
- Create producer validations (empty name and valid save flow)

## Notes

- Main app config still defines:

```properties
spring.application.name=moviestesting
```
