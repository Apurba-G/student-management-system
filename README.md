# 🎓 Student Management System (SMS)

A full-stack, enterprise-grade Student Management System built with **Spring Boot 3.3.4 (Java 21)**, **Angular 21**, **PostgreSQL 16**, and **pgAdmin 4**, fully containerized with **Docker Compose**.

---

## 🎯 Project Purpose & Core Ideology

### 📌 Purpose: Why This System Exists
Academic institutions often struggle with fragmented records, slow report latency, and fragile administrative workflows across disparate spreadsheets or outdated monolithic systems. The **Student Management System (SMS)** was engineered to solve these core operational challenges:

1. **Centralized Student Lifecycle Governance**: Provide administrative staff with a single, authoritative ledger to enroll, update, monitor, and audit students across diverse academic departments with complete historical accuracy.
2. **Real-Time Institutional Visibility**: Deliver instant, single-query executive metrics (Active, Inactive, Suspended, Total registrations) without imposing database lock contention or multi-second report delays.
3. **Enterprise Security & Session Sovereignty**: Protect student privacy and administrative credentials via cryptographic authentication (HMAC-SHA512 JWTs, BCrypt key derivation), strict session invalidation upon password alteration, and emergency self-service recovery.
4. **Developer Pedagogical Benchmark**: Serve as a production-grade blueprint demonstrating modern clean code principles, Domain-Driven Design (DDD), automated Flyway migrations, and reproducible multi-stage Docker deployment.

---

### 🏛️ The 6 Pillars of Engineering Ideology

Every architectural decision and line of code in this repository adheres to six core design philosophies:

1. **Relational Truth & Data Integrity First**
   - The relational database is the supreme authority. Relational constraints (`UNIQUE` email/enrollment, `NOT NULL`, default values) are enforced at the PostgreSQL engine level, guaranteeing that invalid data can never enter the system regardless of application-layer bugs.
   - Schema evolution is governed strictly by version-controlled Flyway migrations (`V1`, `V2`), eliminating dangerous automated DDL updates (`ddl-auto=update`).

2. **Stateless Scalability by Design**
   - No server-side HTTP session state (`HttpSession`) is ever stored in JVM memory. By relying on stateless JWTs coupled with database-backed `token_version` tracking, backend containers can be horizontally scaled, stopped, or restarted behind load balancers with zero disruption or lost sessions.

3. **Strict Separation of Concerns & Unidirectional Data Flow**
   - Presentation (Angular 21 Standalone), Gateway (Nginx Reverse Proxy), Domain Logic (Spring Boot Services), and Data Access (Spring Data JPA) are completely decoupled.
   - Database Entities are never exposed over REST API boundaries; request and response contracts are strictly governed by immutable Data Transfer Objects (DTOs) with JSR-380 Bean Validation.

4. **Defense-in-Depth Security Philosophy**
   - Multi-tiered security protects every layer:
     - **OS / Container**: Alpine Linux runtime executing under an unprivileged user (`smsuser:smsgroup`).
     - **Gateway**: Nginx reverse proxy masks internal port topologies and neutralizes CORS vulnerabilities.
     - **Application**: Stateless Spring Security filter chain with constant-time token verification and BCrypt 72-byte truncation guards.
     - **Persistence**: Hibernate parameterized SQL binds user input purely as binary literal data, mathematically immune to SQL injection.

5. **Mechanical Sympathy & Sub-Second Latency**
   - We design software with deep respect for underlying hardware and database engines:
     - Single JPQL aggregate projection replaces multiple round-trips to compute dashboard statistics in $< 2\text{ms}$.
     - B-Tree indexes provide $O(\log N)$ logarithmic lookup time for frequent queries.
     - RxJS debouncing on client search input prevents API request flooding.
     - Docker multi-stage build caching cuts container build times from minutes to seconds.

6. **Zero Vendor Lock-In & Self-Hosted Autonomy**
   - The entire ecosystem runs identically on local developer machines, on-premise university server rooms, or any major cloud VM (AWS, GCP, Azure) via a single command (`docker compose up -d`). No proprietary cloud services, third-party databases, or vendor-locked SDKs are required.

---

## 🌟 Key Features

- **🔐 Robust JWT Authentication**: Stateless Spring Security 6 with HS512 HMAC signing, BCrypt hashing (72-byte guard), and database-level `token_version` tracking for instant session revocation on password changes.
- **📊 Real-Time Dashboard**: Comprehensive metrics tracking **Total Students**, **Active Students**, **Inactive Students**, and **Suspended Students**, alongside recently enrolled students.
- **⚡ High-Performance Directory & Search**: Paginated and sorted student directory with live debounced search by first name, last name, enrollment number, department, and email.
- **🔑 Admin Management**: Dedicated change password workflow with immediate session invalidation and re-authentication.
- **🐳 Multi-Stage Docker Architecture**: Production-grade alpine containers with non-root security (`smsuser`), persistent volumes, and healthchecks.
- **📖 OpenAPI / Swagger UI Documentation**: Fully documented endpoints with interactive `BearerAuth` token testing.

---

## 📚 Master Architecture & Examination Documentation

Comprehensive guides covering end-to-end execution flows, under-the-hood annotations vs. old Java code, design patterns, and 100+ professor viva voce defense questions:

1. [**Part 1: Architecture, Developer Mindset & Packet Tracing**](docs/01_ARCHITECTURE_DEVELOPER_BLUEPRINT_AND_FLOW.md)
   - Step-by-step developer mental model: Where to start writing code first (Domain-first layered approach).
   - End-to-end packet and execution trace from user click through Nginx, Spring Security, DispatcherServlet, Service, JPA/Hibernate, and PostgreSQL.
   - Docker container bridge network topology and data persistence.
2. [**Part 2: Backend Deep-Dive, Old Java vs. Modern Spring, & Professor Viva Defense**](docs/02_BACKEND_DEEP_DIVE_ANNOTATIONS_PATTERNS_AND_VIVA_QUESTIONS.md)
   - Detailed under-the-hood comparison of modern Spring annotations (`@Transactional`, `@RestController`, `@Repository`, `@Valid`, `@Builder`) vs. legacy Java (Raw JDBC, manual rollback, `HttpServlet`, manual validation).
   - Design patterns unpacked: Repository, DTO, Builder, Factory, Proxy (AOP), Strategy, and Chain of Responsibility.
   - Comprehensive viva voce questions and technical answers for oral exam defense.
3. [**Part 3: Frontend Deep-Dive, Multi-Stage Docker, Database Migrations, & Git Workflow**](docs/03_FRONTEND_DOCKER_SECURITY_AND_GIT_WORKFLOW.md)
   - Modern Angular 21 Standalone architecture, Angular Signals vs. RxJS Observables, functional guards, and HTTP interceptors.
   - Multi-stage Docker build caching, Alpine hardening, non-root `smsuser`, and Nginx reverse proxy.
   - Flyway database migrations and B-Tree indexing strategy.
   - Git staging checklist and standardized commit message template.

---

## 🏗️ Architecture & Services

| Service | Container Name | Technology | Internal Port | Host Port | URL |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Frontend** | `sms-frontend` | Angular 21 + Nginx Alpine | `80` | `4200` | [http://localhost:4200](http://localhost:4200) |
| **Backend API** | `sms-backend` | Spring Boot 3 + Java 21 | `8080` | `8080` | [http://localhost:8080](http://localhost:8080) |
| **Swagger UI** | `sms-backend` | SpringDoc OpenAPI 2.6 | `8080` | `8080` | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) |
| **PostgreSQL** | `sms-postgres` | PostgreSQL 16 Alpine | `5432` | `5433` | `localhost:5433` *(avoids host port 5432)* |
| **pgAdmin 4** | `sms-pgadmin` | dpage/pgadmin4 | `80` | `5050` | [http://localhost:5050](http://localhost:5050) |

---

## 🚀 Quick Start with Docker

### 1. Prerequisites
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (running with WSL 2 on Windows or native Linux/macOS)

### 2. Configure Environment
Copy the example environment template to create your `.env` file:
```bash
cp .env.example .env
```

### 3. Launch the Complete Ecosystem
```bash
docker compose up -d --build
```

### 4. Default Login Credentials

| System | Username / Email | Password | Purpose |
| :--- | :--- | :--- | :--- |
| **SMS Web App** | `admin` | `admin123` | Administrator portal login |
| **pgAdmin 4** | `admin@sms.com` | `admin` | PostgreSQL web database client |
| **PostgreSQL Database** | `postgres` | `postgres` | Host `localhost:5433`, Database: `sms_db` |

---

## 🆘 Forgot Password & Emergency Admin Recovery

If an administrator forgets their password before logging into the system, two recovery paths are available:

### Method 1: Web UI Emergency Recovery (Self-Service)
1. On the login page ([http://localhost:4200/auth/login](http://localhost:4200/auth/login)), click **"Forgot password / Emergency recovery?"** (or navigate to `/auth/forgot-password`).
2. Provide:
   - **Admin Username**: `admin`
   - **Emergency Recovery Key**: Value of `ADMIN_RECOVERY_KEY` from your `.env` (default: `sms_admin_recovery_key_2026`).
   - **New Password**: Enter your new password (minimum 8 characters).
3. Click **Reset Admin Password**. Upon successful validation, the system immediately hashes the new password, revokes all previous JWTs, and redirects to login.

### Method 2: Direct Server CLI Reset (1-Click)
Run the emergency script from the project root:
- **Windows PowerShell**:
  ```powershell
  .\reset-admin-password.ps1
  ```
- **Linux / macOS / WSL**:
  ```bash
  chmod +x reset-admin-password.sh && ./reset-admin-password.sh
  ```
- **Direct Docker Command**:
  ```bash
  docker exec -i sms-postgres psql -U postgres -d sms_db -c "UPDATE admins SET password_hash = '\$2a\$10\$HRM94vqk0lyrcSxFhqgHue07tJnkkpwrN9hK9A.UKbawwyKOxCxYu', token_version = token_version + 1 WHERE username = 'admin';"
  ```
This instantly resets the admin password back to `admin123`.

---


## 📁 Repository Structure

```
student-management-system/
├── compose.yaml                      # Root multi-container orchestration
├── .env.example                      # Secrets and configuration template
├── .env                              # Private active environment variables (git-ignored)
├── README.md                         # Main project documentation
│
├── student-management-backend/       # Spring Boot 3.3.4 + Java 21 REST API
│   ├── Dockerfile                    # Multi-stage Maven build + Eclipse Temurin JRE
│   ├── pom.xml                       # Maven dependencies
│   └── src/                          # Controllers, services, JPA entities, security
│
├── student-management-frontend/      # Angular 21 Single Page Application
│   ├── Dockerfile                    # Multi-stage Node 22 build + Nginx Alpine runtime
│   ├── nginx.conf                    # SPA routing and /api/ reverse proxy
│   └── src/                          # Components, guards, interceptors, services
│
├── database/                         # Database scripts, backup/restore, and ER diagrams
└── docs/                             # System design, architecture, and API docs
```

---

## 🛠️ Common Operations

```bash
# View live status of all services
docker compose ps

# View real-time logs
docker compose logs -f backend
docker compose logs -f frontend

# Restart services
docker compose restart

# Stop all services (preserves database data volume)
docker compose down

# Stop all services and remove volumes
docker compose down -v
```
