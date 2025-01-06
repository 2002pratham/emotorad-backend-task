package com.example.emotorad.Backend_Task.exception;

import com.example.emotorad.Backend_Task.dto.ContactResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Add this annotation to enable Mockito
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void testUnexpectedResponseWhenRequestIsInvalid() {
        // Simulate InvalidContactRequestException
        InvalidContactRequestException exception = new InvalidContactRequestException("Invalid phone number format");

        ResponseEntity<ContactResponse> response = globalExceptionHandler.handleInvalidContactRequestException(exception);

        // Check if the response status is correct
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessages().contains("Unexpected error occurred. Please try again later."));
    }

    @Test
    void testUnexpectedBehaviorWhenNoContactFound() {
        // Simulate ContactNotFoundException
        ContactNotFoundException exception = new ContactNotFoundException("Contact ID 123");

        ResponseEntity<ContactResponse> response = globalExceptionHandler.handleContactNotFoundException(exception);

        // Check if the response status is correct
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().getMessages().contains("Contact not found or already deleted."));
    }

    @Test
    void testCatchAllForGenericErrors() {
        // Simulate a generic exception
        Exception exception = new Exception("Random exception");

        ResponseEntity<ContactResponse> response = globalExceptionHandler.handleGenericException(exception);

        // Check if the response status is correct
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().getMessages().contains("Unforeseen issue detected, please try again."));
    }
}
