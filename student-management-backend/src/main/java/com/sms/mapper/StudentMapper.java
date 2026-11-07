package com.sms.mapper;

import com.sms.dto.request.StudentCreateRequest;
import com.sms.dto.request.StudentUpdateRequest;
import com.sms.dto.response.StudentResponse;
import com.sms.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(StudentCreateRequest request) {
        if (request == null) return null;
        return Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .department(request.getDepartment())
                .enrollmentNumber(request.getEnrollmentNumber())
                .status(request.getStatus() != null ? request.getStatus() : "ACTIVE")
                .build();
    }

    public StudentResponse toResponse(Student entity) {
        if (entity == null) return null;
        return StudentResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .dateOfBirth(entity.getDateOfBirth())
                .department(entity.getDepartment())
                .enrollmentNumber(entity.getEnrollmentNumber())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public void updateEntityFromRequest(Student entity, StudentUpdateRequest request) {
        if (entity == null || request == null) return;
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setEmail(request.getEmail());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setDateOfBirth(request.getDateOfBirth());
        entity.setDepartment(request.getDepartment());
        entity.setEnrollmentNumber(request.getEnrollmentNumber());
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
    }
}
