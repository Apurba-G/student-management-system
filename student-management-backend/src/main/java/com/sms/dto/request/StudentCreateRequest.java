package com.sms.dto.request;

import com.sms.validation.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCreateRequest {

    @NotBlank(message = ValidationMessages.FIRST_NAME_REQUIRED)
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = ValidationMessages.LAST_NAME_REQUIRED)
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
    @Email(message = ValidationMessages.EMAIL_INVALID)
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    @NotBlank(message = ValidationMessages.DEPARTMENT_REQUIRED)
    @Size(max = 100, message = "Department must not exceed 100 characters")
    private String department;

    @NotBlank(message = ValidationMessages.ENROLLMENT_REQUIRED)
    @Size(max = 50, message = "Enrollment number must not exceed 50 characters")
    private String enrollmentNumber;

    @NotBlank(message = ValidationMessages.STATUS_REQUIRED)
    private String status;
}
