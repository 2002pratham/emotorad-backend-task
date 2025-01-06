package com.example.emotorad.Backend_Task.controller;

import com.example.emotorad.Backend_Task.dto.ContactRequest;
import com.example.emotorad.Backend_Task.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    private static final Logger logger = Logger.getLogger(ContactController.class.getName());

    @PostMapping("/identify")
    public ResponseEntity<Map<String, Object>> identifyContact(
            @RequestBody @Valid ContactRequest request, BindingResult bindingResult) {

        // Log the request body
        logger.info("Received contact identification request: " + request);

        // Custom validation for missing email or phone number
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            logger.warning("Validation failed: email must not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Validation failed", "details", "email: must not be empty"));
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            logger.warning("Validation failed: phoneNumbers must not be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Validation failed", "details", "phoneNumbers: must not be empty"));
        }

        // If other validation fails, return BAD_REQUEST response
        if (bindingResult.hasErrors()) {
            logger.warning("Validation failed: " + bindingResult.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Validation failed", "details", bindingResult.getAllErrors()));
        }

        // Extract email and phone number from the validated request
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();

        // Process the contact and return the response
        Map<String, Object> response = contactService.processContact(email, phoneNumber);

        logger.info("Processed contact identification successfully: " + response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
