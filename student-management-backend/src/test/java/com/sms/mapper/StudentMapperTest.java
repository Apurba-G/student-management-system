package com.sms.mapper;

import com.sms.dto.request.StudentCreateRequest;
import com.sms.dto.response.StudentResponse;
import com.sms.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    void shouldMapCreateRequestToEntity() {
        StudentCreateRequest request = StudentCreateRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .department("Computer Science")
                .enrollmentNumber("CS101")
                .status("ACTIVE")
                .build();

        Student entity = mapper.toEntity(request);

        assertNotNull(entity);
        assertEquals("John", entity.getFirstName());
        assertEquals("Doe", entity.getLastName());
        assertEquals("john.doe@example.com", entity.getEmail());
    }

    @Test
    void shouldMapEntityToResponse() {
        Student student = Student.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .department("Physics")
                .enrollmentNumber("PHY102")
                .status("ACTIVE")
                .build();

        StudentResponse response = mapper.toResponse(student);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Jane", response.getFirstName());
        assertEquals("jane.smith@example.com", response.getEmail());
    }
}
