# Student Management System - Backend

Spring Boot 3.3 RESTful API backend service providing student management, pagination, search, validation, and database persistence.

## Architecture
- **Layered Architecture**: Controller -> Service -> Repository -> Database
- **DTO Mapping**: Clean separation between persistence entities and API contracts
- **Exception Handling**: Global exception handler with structured responses
- **Documentation**: Springdoc OpenAPI / Swagger UI

## Endpoints
- `GET /api/v1/students` : Paginated and searchable list of students
- `GET /api/v1/students/{id}` : Get details of a single student
- `POST /api/v1/students` : Register a new student
- `PUT /api/v1/students/{id}` : Update student information
- `DELETE /api/v1/students/{id}` : Delete student by ID

## Swagger Documentation
Once the server is running, navigate to:
`http://localhost:8080/swagger-ui/index.html`

## Running the Application
```bash
./mvnw spring-boot:run
```
