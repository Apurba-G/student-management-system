package com.sms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.dto.request.StudentCreateRequest;
import com.sms.dto.response.StudentResponse;
import com.sms.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetStudentById() throws Exception {
        StudentResponse response = StudentResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .department("CS")
                .enrollmentNumber("CS001")
                .status("ACTIVE")
                .build();

        when(studentService.getStudentById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void shouldCreateStudent() throws Exception {
        StudentCreateRequest request = StudentCreateRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .department("CS")
                .enrollmentNumber("CS001")
                .status("ACTIVE")
                .build();

        StudentResponse response = StudentResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .department("CS")
                .enrollmentNumber("CS001")
                .status("ACTIVE")
                .build();

        when(studentService.createStudent(any(StudentCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
