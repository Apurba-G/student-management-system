
package com.sms.exception;

public class DuplicateEnrollmentNumberException extends RuntimeException {

    public DuplicateEnrollmentNumberException(String enrollmentNumber) {
        super(
                "A student with enrollment number '"
                        + enrollmentNumber
                        + "' already exists."
        );
    }
}
