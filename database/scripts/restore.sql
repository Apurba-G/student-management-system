-- Database Restoration Script for Student Management System
-- Run via: pg_restore -U postgres -d sms_db -v -c sms_backup.dump

-- Pre-restore check
SELECT pg_terminate_backend(pid) 
FROM pg_stat_activity 
WHERE datname = 'sms_db' AND pid <> pg_backend_pid();

-- Verification of table counts post-restore
SELECT count(*) AS total_students FROM students;
