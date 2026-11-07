# Data Dictionary

| Column | Type | Constraints | Description |
|---|---|---|---|
| id | BIGSERIAL | PK, NOT NULL | Unique student identifier |
| first_name | VARCHAR(50) | NOT NULL | Student given name |
| last_name | VARCHAR(50) | NOT NULL | Student family name |
| email | VARCHAR(100) | NOT NULL, UNIQUE | Student primary email |
| phone_number | VARCHAR(20) | NULL | Contact phone |
| date_of_birth | DATE | NULL | Date of birth |
| department | VARCHAR(100) | NOT NULL | Department name |
| enrollment_number | VARCHAR(50) | NOT NULL, UNIQUE | Institutional ID |
| status | VARCHAR(20) | NOT NULL | ACTIVE, INACTIVE, SUSPENDED |
| created_at | TIMESTAMP | NOT NULL | Record creation timestamp |
| updated_at | TIMESTAMP | NOT NULL | Record last update timestamp |
