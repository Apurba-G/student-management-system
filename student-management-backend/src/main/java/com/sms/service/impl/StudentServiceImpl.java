package com.sms.service.impl;

import com.sms.dto.request.StudentCreateRequest;
import com.sms.dto.request.StudentUpdateRequest;
import com.sms.dto.response.StudentResponse;
import com.sms.entity.Student;
import com.sms.exception.DuplicateEmailException;
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

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponse> getAllStudents(int page, int size, String sortBy, String sortDir, String search) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Student> studentsPage;
        if (search != null && !search.trim().isEmpty()) {
            studentsPage = studentRepository.searchStudents(search.trim(), pageable);
        } else {
            studentsPage = studentRepository.findAll(pageable);
        }

        return studentsPage.map(studentMapper::toResponse);
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

        Student student = studentMapper.toEntity(request);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponse(savedStudent);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        if (studentRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new DuplicateEmailException(request.getEmail());
        }

        studentMapper.updateEntityFromRequest(student, request);
        Student updatedStudent = studentRepository.save(student);
        return studentMapper.toResponse(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }
}
