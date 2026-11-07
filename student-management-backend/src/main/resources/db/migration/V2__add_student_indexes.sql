CREATE INDEX IF NOT EXISTS idx_student_email ON students(email);
CREATE INDEX IF NOT EXISTS idx_student_enrollment ON students(enrollment_number);
CREATE INDEX IF NOT EXISTS idx_student_department ON students(department);
CREATE INDEX IF NOT EXISTS idx_student_status ON students(status);
