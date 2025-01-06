package com.example.emotorad.Backend_Task.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ContactRequest {

    @NotNull(message = "Email cannot be null")  // Ensures email is not null
    @Email(message = "Invalid email format")    // Validates the email format
    private String email;

    @NotNull(message = "Phone number cannot be null")  // Ensures phone number is not null
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number format") // Validates phone number (10 digits)
    private String phoneNumber;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
