# Request Response Flow

1. Browser issues HTTP request to `/api/v1/students`.
2. Spring Boot `CorsConfig` validates origin header.
3. `StudentController` routes request through Spring Validation.
4. `StudentServiceImpl` queries `StudentRepository`.
5. Response transformed into DTO via `StudentMapper`.
6. Result serialized as JSON response to client.
