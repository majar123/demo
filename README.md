# Pet Clinic Demo

A Quarkus REST API for managing pet owners and their pets. Built with Java 21, Quarkus 3.x, and PostgreSQL.

## Project Structure

```
demo/
├── entity/     # JPA entities (Owner, Pet)
├── service/    # Business logic & repositories
└── api/        # REST resources, DTOs, mappers
```

## API Endpoints

### Owners — `/owners`

| Method | Path          | Description           |
|--------|---------------|-----------------------|
| GET    | `/owners`     | List all owners |
| GET    | `/owners/{id}`| Get owner by ID       |
| POST   | `/owners`     | Create owner          |
| PUT    | `/owners/{id}`| Update owner          |
| DELETE | `/owners/{id}`| Delete owner          |

### Pets — `/pets`

| Method | Path                    | Description              |
|--------|-------------------------|--------------------------|
| GET    | `/pets`                 | List all pets            |
| GET    | `/pets/{id}`            | Get pet by ID            |
| GET    | `/pets/owner/{ownerId}` | Get all pets of an owner |
| POST   | `/pets`                 | Create pet               |
| PUT    | `/pets/{id}`            | Update pet               |
| DELETE | `/pets/{id}`            | Delete pet               |

### Pet types

`DOG`, `CAT`, `BIRD`, `RABBIT`, `OTHER`

## Configuration

### `api/src/main/resources/application.properties` (production)

| Property | Description | Default |
|---|---|---|
| `quarkus.datasource.db-kind` | Database type | `postgresql` |
| `quarkus.datasource.username` | DB username | — |
| `quarkus.datasource.password` | DB password | — |
| `quarkus.datasource.jdbc.url` | JDBC connection URL | `jdbc:postgresql://localhost:5432/pets` |
| `quarkus.hibernate-orm.schema-management.strategy` | Schema update strategy | `update` |

### `application.properties` (tests)

Tests use an in-memory H2 database — no PostgreSQL needed to run tests.

| Property | Value |
|---|---|
| `quarkus.datasource.db-kind` | `h2` |
| `quarkus.datasource.jdbc.url` | `jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1` |
| `quarkus.hibernate-orm.database.generation` | `drop-and-create` |

## Running

**Dev mode** (live reload):
```shell
./mvnw quarkus:dev
```

App: http://localhost:8080  
Swagger UI: http://localhost:8080/q/swagger-ui  

### Health endpoints

| Endpoint | Description |
|---|---|
| `GET /q/health` | Overall health status |
| `GET /q/health/live` | Liveness check |
| `GET /q/health/ready` | Readiness check (includes DB connectivity) |

**Build & run:**
```shell
./mvnw package
java -jar api/target/quarkus-app/quarkus-run.jar
```

## Running Tests

```shell
./mvnw test
```
