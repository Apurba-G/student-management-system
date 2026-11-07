package com.sms.repository;

import com.sms.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Student> findByEnrollmentNumber(String enrollmentNumber);

    boolean existsByEnrollmentNumber(String enrollmentNumber);

    boolean existsByEnrollmentNumberAndIdNot(String enrollmentNumber, Long id);

    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.department) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.enrollmentNumber) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Student> searchStudents(@Param("search") String search, Pageable pageable);
}
