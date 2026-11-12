
package com.sms.service.impl;

import com.sms.dto.request.StudentCreateRequest;
import com.sms.dto.request.StudentUpdateRequest;
import com.sms.dto.response.StudentResponse;

import com.sms.entity.Student;

import com.sms.exception.DuplicateEmailException;
import com.sms.exception.DuplicateEnrollmentNumberException;
import com.sms.exception.StudentNotFoundException;

import com.sms.mapper.StudentMapper;
import com.sms.repository.StudentRepository;
import com.sms.service.StudentService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private static final int MAX_PAGE_SIZE = 100;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "firstName",
            "lastName",
            "email",
            "department",
            "enrollmentNumber",
            "status",
            "createdAt",
            "updatedAt"
    );

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponse> getAllStudents(
            int page,
            int size,
            String sortBy,
            String sortDir,
            String search
    ) {

        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page number cannot be negative"
            );
        }

        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and " + MAX_PAGE_SIZE
            );
        }

        if (sortBy == null || !ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException(
                    "Invalid sorting field: " + sortBy
            );
        }

        if (sortDir == null
                || !(sortDir.equalsIgnoreCase("ASC")
                || sortDir.equalsIgnoreCase("DESC"))) {

            throw new IllegalArgumentException(
                    "Sort direction must be ASC or DESC"
            );
        }

        Sort.Direction direction =
                Sort.Direction.fromString(sortDir);

        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Student> studentsPage;

        if (search != null && !search.isBlank()) {
            String trimmed = search.trim();
            studentsPage = studentRepository.searchStudents(
                    trimmed,
                    trimmed.length(),
                    pageable
            );
        } else {
            studentsPage = studentRepository.findAll(pageable);
        }

        return studentsPage.map(studentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public com.sms.dto.response.DashboardStatsResponse getDashboardStats() {
        long total = studentRepository.count();
        long active = studentRepository.countByStatus("ACTIVE");
        long inactive = studentRepository.countByStatus("INACTIVE");
        long suspended = studentRepository.countByStatus("SUSPENDED");
        return com.sms.dto.response.DashboardStatsResponse.builder()
                .totalStudents(total)
                .activeStudents(active)
                .inactiveStudents(inactive)
                .suspendedStudents(suspended)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        return studentMapper.toResponse(student);
    }

    @Override
    public StudentResponse createStudent(StudentCreateRequest request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        if (studentRepository.existsByEnrollmentNumber(
                request.getEnrollmentNumber())) {

            throw new DuplicateEnrollmentNumberException(
                    request.getEnrollmentNumber()
            );
        }

        Student student = studentMapper.toEntity(request);

        Student savedStudent = studentRepository.save(student);

        return studentMapper.toResponse(savedStudent);
    }

    @Override
    public StudentResponse updateStudent(
            Long id,
            StudentUpdateRequest request
    ) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        if (studentRepository.existsByEmailAndIdNot(
                request.getEmail(),
                id
        )) {

            throw new DuplicateEmailException(request.getEmail());
        }

        if (studentRepository.existsByEnrollmentNumberAndIdNot(
                request.getEnrollmentNumber(),
                id
        )) {

            throw new DuplicateEnrollmentNumberException(
                    request.getEnrollmentNumber()
            );
        }

        studentMapper.updateEntityFromRequest(student, request);

        Student updatedStudent = studentRepository.save(student);

        return studentMapper.toResponse(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        studentRepository.delete(student);
    }
}
