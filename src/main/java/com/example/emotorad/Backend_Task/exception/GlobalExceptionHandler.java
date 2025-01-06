package com.example.emotorad.Backend_Task.exception;

import com.example.emotorad.Backend_Task.dto.ContactResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidContactRequestException.class)
    public ResponseEntity<ContactResponse> handleInvalidContactRequestException(InvalidContactRequestException ex) {
        logger.error("Invalid Contact Request: ", ex);
        ContactResponse errorResponse = new ContactResponse(
                1001L,
                Collections.singletonList("Request contains invalid data."),
                Collections.singletonList("Validation failed for input."),
                Collections.emptyList()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ContactResponse> handleContactNotFoundException(ContactNotFoundException ex) {
        logger.error("Contact Not Found: ", ex);
        ContactResponse errorResponse = new ContactResponse(
                1002L,
                Collections.singletonList("No contact data available for the provided input."),
                Collections.singletonList("Check the details and try again."),
                Collections.emptyList()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ContactResponse> handleGenericException(Exception ex) {
        logger.error("Unexpected Error: ", ex);
        ContactResponse errorResponse = new ContactResponse(
                9999L,
                Collections.singletonList("System encountered an unexpected error."),
                Collections.singletonList("Error ID: X8394, please report to support."),
                Collections.emptyList()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
