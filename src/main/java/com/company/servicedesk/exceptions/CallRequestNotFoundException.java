package com.company.servicedesk.exceptions;

public class CallRequestNotFoundException extends RuntimeException {
    public CallRequestNotFoundException(String message) {
        super(message);
    }
}
