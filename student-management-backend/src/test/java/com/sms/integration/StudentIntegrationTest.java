package com.sms.integration;

import com.sms.StudentManagementApplication;
import com.sms.entity.Student;
import com.sms.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = StudentManagementApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StudentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void shouldFetchStudentsSuccessfully() throws Exception {
        Student student = Student.builder()
                .firstName("Integration")
                .lastName("Test")
                .email("integration@sms.com")
                .department("Engineering")
                .enrollmentNumber("INT999")
                .status("ACTIVE")
                .build();
        studentRepository.save(student);

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk());
    }
}
