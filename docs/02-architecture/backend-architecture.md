# Backend Architecture

Follows classic Controller-Service-Repository patterns:
- **Presentation Layer**: `StudentController` exposes REST endpoints.
- **Business Layer**: `StudentServiceImpl` handles transaction logic, business validations, and DTO mappings.
- **Data Access Layer**: `StudentRepository` leveraging Spring Data JPA.
- **Exception Layer**: `GlobalExceptionHandler` with `@RestControllerAdvice`.
