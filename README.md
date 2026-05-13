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

## BOT Pattern For E2E Tests

This project uses a BOT pattern for Selenium E2E tests to keep test scenarios readable and reduce duplicated browser automation logic.

### Why this pattern

- Tests focus on scenario intent and assertions.
- Bots encapsulate navigation, waits, selectors, and user interactions.
- Shared Selenium setup is centralized and reusable.

### Current BOT structure

- `src/test/java/com/kikesoft/moviestesting/e2e/bots/BaseBot.java`
	- Shared behavior for route opening and wait helpers.
- `src/test/java/com/kikesoft/moviestesting/e2e/bots/ProducersBot.java`
	- Producer-specific user actions and queries.
- `src/test/java/com/kikesoft/moviestesting/e2e/support/DriverFactory.java`
	- WebDriver creation.
- `src/test/java/com/kikesoft/moviestesting/e2e/support/WaitSupport.java`
	- Standardized Selenium wait creation and helper conditions.

### Responsibility split

- Test classes:
	- Describe behavior.
	- Execute high-level bot actions.
	- Assert expected outcomes.
- Bot classes:
	- Hide CSS selectors and element lookups.
	- Handle page synchronization/waits.
	- Expose business-level actions.

## How To Add More E2E Tests

Follow this flow when creating new tests:

1. Identify a user flow to validate (for example, a create/edit/list/detail flow).
2. Add or extend a bot class under `src/test/java/com/kikesoft/moviestesting/e2e/bots`.
3. Keep selectors and navigation inside the bot, not in test methods.
4. Create a test class under the related package (for example, `src/test/java/com/kikesoft/moviestesting/e2e/producers`).
5. Use `DriverFactory` to create the browser driver.
6. Use bot methods in tests, then assert behavior in the test class.
7. Run the new tests locally and verify stability.

### Minimal test template

```java
@Test
void shouldValidateExpectedBehavior() {
		WebDriver driver = DriverFactory.createChromeDriver();

		try {
				ProducersBot producersBot = new ProducersBot(driver);

				producersBot.openList();
				producersBot.waitUntilListReady();

				assertTrue(producersBot.isListTitleVisible());
		} finally {
				driver.quit();
		}
}
```

### Good practices for new tests

- Keep one test focused on one business behavior.
- Prefer explicit wait logic inside bots instead of sleeps.
- Reuse existing bot methods before adding new ones.
- Add new bot methods only when they represent reusable behavior.
- Fail fast with clear assertion messages.

## Notes

- Main app config still defines:

```properties
spring.application.name=moviestesting
```
