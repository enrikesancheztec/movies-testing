# moviestesting

Spring Boot project created from [Spring Initializr](https://start.spring.io/) and used only to run automated tests for **movies-app**:

- Target repository: https://github.com/enrikesancheztec/movies-app/

## Purpose

This repository is a **test automation project only**.

- It is not intended to run business APIs.
- It is used to execute Selenium E2E validations against a running movies-app web instance.

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
- `org.seleniumhq.selenium:selenium-java` (scope `test`)
- `io.cucumber:cucumber-java` (scope `test`)
- `io.cucumber:cucumber-junit-platform-engine` (scope `test`)

## Prerequisites

- Java 17 installed and available in `PATH`
- Permissions to run `./mvnw` on macOS/Linux
- movies-app web application running and reachable from this machine

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

## Testing Pattern

This project uses **Cucumber + BOT pattern** for all Selenium E2E tests.

### Architecture

- Feature files define behavior in Gherkin.
- Step definitions map behavior to bot actions and assertions.
- Hooks manage browser lifecycle per scenario.
- Bots encapsulate selectors, waits, and page interactions.

### Key Structure

- `src/test/resources/features/`
	- Feature files by domain (for example, `features/producers`).
- `src/test/java/com/kikesoft/moviestesting/e2e/cucumber/`
	- Cucumber suite, hooks, and scenario context.
- `src/test/java/com/kikesoft/moviestesting/e2e/cucumber/<domain>/`
	- Step definitions by domain.
- `src/test/java/com/kikesoft/moviestesting/e2e/bots/`
	- BOT classes with reusable Selenium behavior.
- `src/test/java/com/kikesoft/moviestesting/e2e/support/`
	- Shared driver and wait utilities.

## How To Add New Tests

1. Identify the user behavior to validate.
2. Add or update a feature file under `src/test/resources/features/<domain>/`.
3. Add or update step definitions under `src/test/java/com/kikesoft/moviestesting/e2e/cucumber/<domain>/`.
4. Reuse existing bot methods from `src/test/java/com/kikesoft/moviestesting/e2e/bots/`.
5. If needed, add new high-level bot methods. Do not add selectors in step definitions.
6. Keep waits in bots/support classes only.
7. Run Cucumber locally and confirm scenarios pass.

### Minimal Feature Example

```gherkin
@producers
Scenario: Producers list page is visible
	Given the user opens the producers list page
	Then the producers list title should be visible
```

### Rules For Consistency

- One scenario should validate one behavior.
- Use business-readable scenario names.
- Reuse steps when possible; avoid duplicated phrases.
- Keep selectors only in bot classes.
- Keep synchronization logic in bot/support classes.

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

### Run full test suite

macOS/Linux:

```bash
./mvnw test
```

Windows PowerShell/CMD:

```powershell
mvnw.cmd test
```

### Run full Cucumber E2E suite

macOS/Linux:

```bash
./mvnw -Dtest=CucumberTestSuite test
```

Windows PowerShell/CMD:

```powershell
mvnw.cmd -Dtest=CucumberTestSuite test
```

### Run a specific set of scenarios by tags

macOS/Linux:

```bash
./mvnw -Dtest=CucumberTestSuite -Dcucumber.filter.tags="@producers and @create" test
```

Windows PowerShell/CMD:

```powershell
mvnw.cmd -Dtest=CucumberTestSuite -Dcucumber.filter.tags="@producers and @create" test
```

### Run a specific scenario by name

macOS/Linux:

```bash
./mvnw -Dtest=CucumberTestSuite -Dcucumber.filter.name="User creates a producer successfully" test
```

Windows PowerShell/CMD:

```powershell
mvnw.cmd -Dtest=CucumberTestSuite -Dcucumber.filter.name="User creates a producer successfully" test
```

### Run Cucumber suite with custom target URL

macOS/Linux:

```bash
./mvnw -Dweb.base.url=http://10.0.0.5:3000 -Dtest=CucumberTestSuite test
```

Windows PowerShell/CMD:

```powershell
mvnw.cmd -Dweb.base.url=http://10.0.0.5:3000 -Dtest=CucumberTestSuite test
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
