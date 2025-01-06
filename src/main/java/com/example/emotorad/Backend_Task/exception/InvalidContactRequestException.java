package com.example.emotorad.Backend_Task.exception;

public class InvalidContactRequestException extends RuntimeException {

    public InvalidContactRequestException(String message) {
        super(message);
    }
}
