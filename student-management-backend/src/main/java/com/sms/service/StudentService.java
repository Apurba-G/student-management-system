package com.sms.service;

import com.sms.dto.request.StudentCreateRequest;
import com.sms.dto.request.StudentUpdateRequest;
import com.sms.dto.response.DashboardStatsResponse;
import com.sms.dto.response.StudentResponse;
import org.springframework.data.domain.Page;

public interface StudentService {
    Page<StudentResponse> getAllStudents(int page, int size, String sortBy, String sortDir, String search);
    DashboardStatsResponse getDashboardStats();
    StudentResponse getStudentById(Long id);
    StudentResponse createStudent(StudentCreateRequest request);
    StudentResponse updateStudent(Long id, StudentUpdateRequest request);
    void deleteStudent(Long id);
}
