package com.sms.service;

import com.sms.dto.request.StudentCreateRequest;
import com.sms.dto.response.StudentResponse;
import com.sms.entity.Student;
import com.sms.exception.DuplicateEmailException;
import com.sms.mapper.StudentMapper;
import com.sms.repository.StudentRepository;
import com.sms.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentResponse studentResponse;
    private StudentCreateRequest createRequest;

    @BeforeEach
    void setUp() {
        student = Student.builder().id(1L).email("bob@example.com").build();
        studentResponse = StudentResponse.builder().id(1L).email("bob@example.com").build();
        createRequest = StudentCreateRequest.builder().email("bob@example.com").build();
    }

    @Test
    void shouldGetStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentMapper.toResponse(student)).thenReturn(studentResponse);

        StudentResponse result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenCreatingWithDuplicateEmail() {
        when(studentRepository.existsByEmail("bob@example.com")).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> studentService.createStudent(createRequest));
        verify(studentRepository, never()).save(any());
    }
}
