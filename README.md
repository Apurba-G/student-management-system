# Student Management System (SMS)

A full-stack, enterprise-grade Student Management System built with Spring Boot 3, Angular 18/21, PostgreSQL, and Docker.

## Project Structure
- `student-management-frontend/`: Modern Angular SPA with lazy-loaded modules, reactive forms, and responsive design.
- `student-management-backend/`: Spring Boot REST API with Spring Data JPA, Hibernate, validation, and Swagger UI.
- `database/`: PostgreSQL docker configurations, backup/restore scripts, and ER diagrams.
- `docs/`: Comprehensive technical documentation covering requirements, architecture, API, testing, and deployment.

## Quick Start

### 1. Backend Service
```bash
cd student-management-backend
./mvnw spring-boot:run
```
Backend API will be accessible at: `http://localhost:8080/api/v1/students`
Swagger UI: `http://localhost:8080/swagger-ui/index.html`

### 2. Frontend Application
```bash
cd student-management-frontend
npm start
```
Frontend Web UI will be accessible at: `http://localhost:4200`
