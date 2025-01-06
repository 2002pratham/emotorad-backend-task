package com.example.emotorad.Backend_Task.dto;

import java.util.List;

public class ContactResponse {

    private Long errorCode;  // Changed from int to Long
    private List<String> messages;
    private List<String> details;
    private List<Object> additionalInfo;

    // Constructor
    public ContactResponse(Long errorCode, List<String> messages, List<String> details, List<Object> additionalInfo) {
        this.errorCode = errorCode;
        this.messages = messages;
        this.details = details;
        this.additionalInfo = additionalInfo;
    }

    // Getters and Setters
    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public List<Object> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(List<Object> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
