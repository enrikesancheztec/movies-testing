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

## Cucumber And Gherkin Overview

This project uses Cucumber as the E2E test runner and Gherkin as the language for writing executable business scenarios.

- Cucumber maps human-readable scenario steps (Given/When/Then) to Java step definitions.
- Gherkin is the plain-language syntax used in `.feature` files.

Official documentation:

- Cucumber docs: https://cucumber.io/docs
- Cucumber JVM docs: https://cucumber.io/docs/installation/java/
- Gherkin reference: https://cucumber.io/docs/gherkin/reference/

## Run Tests

Default test execution is Cucumber-first and runs the `CucumberTestSuite` entry point.

### Run all Cucumber tests (macOS/Linux)

```bash
./mvnw test
```

## Add New Tests

Follow this workflow to add a new Cucumber test:

1. Create or update a feature file under `src/test/resources/features`.
2. Write scenarios using Gherkin syntax (`Feature`, `Scenario`, `Given`, `When`, `Then`).
3. Reuse an existing tag (for example `@producers`) or add a new tag.
4. Implement matching Java step definitions under `src/test/java/com/kikesoft/moviestesting/e2e/cucumber`.
5. If browser setup is needed, reuse existing hooks (`@Before`/`@After`) patterns from current steps.
6. Run a dry-run check to validate glue mapping and ensure no undefined steps.
7. Run the full suite to validate runtime behavior.

### Example: Add a new feature file

Create a file like `src/test/resources/features/movies/list_movies.feature`:

```gherkin
@movies
Feature: List movies

	Scenario: Display movies list page text
		Given I open the movies list page
		Then I should see "Movies List" text on the page
```

Then implement corresponding step definitions in Java.

### Validate newly added tests

Dry-run mapping check (fast, no browser execution):

```bash
./mvnw -Dtest=CucumberTestSuite test -Dcucumber.execution.dry-run=true
```

Full runtime validation:

```bash
./mvnw test
```

### Run all Cucumber tests (Windows PowerShell/CMD)

```powershell
mvnw.cmd test
```

### Run Cucumber suite explicitly (macOS/Linux)

```bash
./mvnw -Dtest=CucumberTestSuite test
```

### Run Cucumber suite explicitly (Windows PowerShell/CMD)

```powershell
mvnw.cmd -Dtest=CucumberTestSuite test
```

### Run tagged Cucumber scenarios (macOS/Linux)

```bash
./mvnw test -Dcucumber.filter.tags="@producers"
```

### Run tagged Cucumber scenarios (Windows PowerShell/CMD)

```powershell
mvnw.cmd test -Dcucumber.filter.tags="@producers"
```

### Run tagged Cucumber scenarios with exclusions (macOS/Linux)

```bash
./mvnw test -Dcucumber.filter.tags="@producers and not @wip"
```

### Run tagged Cucumber scenarios with exclusions (Windows PowerShell/CMD)

```powershell
mvnw.cmd test -Dcucumber.filter.tags="@producers and not @wip"
```

### Run Cucumber tests with custom target URL (macOS/Linux)

```bash
./mvnw test -Dweb.base.url=http://10.0.0.5:3000
```

### Run Cucumber tests with custom target URL (Windows PowerShell/CMD)

```powershell
mvnw.cmd test -Dweb.base.url=http://10.0.0.5:3000
```

### Run tagged Cucumber tests with custom target URL (macOS/Linux)

```bash
./mvnw test -Dweb.base.url=http://10.0.0.5:3000 -Dcucumber.filter.tags="@producers"
```

### Run tagged Cucumber tests with custom target URL (Windows PowerShell/CMD)

```powershell
mvnw.cmd test -Dweb.base.url=http://10.0.0.5:3000 -Dcucumber.filter.tags="@producers"
```

### CI recommendation

Use the same default command in CI for deterministic Cucumber-first execution:

```bash
./mvnw test
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
