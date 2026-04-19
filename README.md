# moviestesting

Spring Boot project created from [Spring Initializr](https://start.spring.io/) to implement and run automated tests for the **movies-app** application:

- Target repository: https://github.com/enrikesancheztec/movies-app/

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

## Build

### macOS / Linux

```bash
./mvnw clean verify
```

### Windows (PowerShell / CMD)

```powershell
mvnw.cmd clean verify
```

## Run the Project

### macOS / Linux

```bash
./mvnw spring-boot:run
```

### Windows (PowerShell / CMD)

```powershell
mvnw.cmd spring-boot:run
```

## Run Tests

### macOS / Linux

```bash
./mvnw test
```

### Windows (PowerShell / CMD)

```powershell
mvnw.cmd test
```

## Packaged Artifact

When building the project, Maven generates the artifact at:

- `target/moviestesting-0.0.1-SNAPSHOT.jar`

You can run it with:

```bash
java -jar target/moviestesting-0.0.1-SNAPSHOT.jar
```

## Notes

- This project is prepared as a base to add tests (unit, integration, or end-to-end) focused on validating `movies-app` behavior.
- The current configuration file (`src/main/resources/application.properties`) defines:

```properties
spring.application.name=moviestesting
```
