# PostgreSQL Docker Setup

This directory contains the configurations and initialization scripts for PostgreSQL database container.

## Container Details
- Image: `postgres:16-alpine`
- Default Port: `5432`
- Default Database: `sms_db`
- Default User: `postgres`

## Startup Commands
```bash
# Start container using Docker Compose
docker compose up -d

# Check container status
docker ps -f name=sms-postgres

# View container logs
docker logs -f sms-postgres
```
