package com.sms.repository;

import com.sms.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void shouldSaveAndFindStudentByEmail() {
        Student student = Student.builder()
                .firstName("Alice")
                .lastName("Wonder")
                .email("alice@example.com")
                .department("Mathematics")
                .enrollmentNumber("MATH01")
                .status("ACTIVE")
                .build();

        studentRepository.save(student);

        Optional<Student> found = studentRepository.findByEmail("alice@example.com");
        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().getFirstName());
        assertTrue(studentRepository.existsByEmail("alice@example.com"));
    }
}
