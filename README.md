# 🎓 Student Management System (SMS)

A full-stack, enterprise-grade Student Management System built with **Spring Boot 3.3.4 (Java 21)**, **Angular 21**, **PostgreSQL 16**, and **pgAdmin 4**, fully containerized with **Docker Compose**.

---

## 🌟 Key Features

- **🔐 Robust JWT Authentication**: Stateless Spring Security 6 with HS512 HMAC signing, BCrypt hashing (72-byte guard), and database-level `token_version` tracking for instant session revocation on password changes.
- **📊 Real-Time Dashboard**: Comprehensive metrics tracking **Total Students**, **Active Students**, **Inactive Students**, and **Suspended Students**, alongside recently enrolled students.
- **⚡ High-Performance Directory & Search**: Paginated and sorted student directory with live debounced search by first name, last name, enrollment number, department, and email.
- **🔑 Admin Management**: Dedicated change password workflow with immediate session invalidation and re-authentication.
- **🐳 Multi-Stage Docker Architecture**: Production-grade alpine containers with non-root security (`smsuser`), persistent volumes, and healthchecks.
- **📖 OpenAPI / Swagger UI Documentation**: Fully documented endpoints with interactive `BearerAuth` token testing.

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
