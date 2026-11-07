-- Database Backup Script for Student Management System
-- Run via: pg_dump -U postgres -d sms_db -F c -b -v -f sms_backup.dump

-- Pre-backup statistics
SELECT schemaname, relname, n_live_tup 
FROM pg_stat_user_tables 
ORDER BY n_live_tup DESC;

-- Schema export verification query
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public';
