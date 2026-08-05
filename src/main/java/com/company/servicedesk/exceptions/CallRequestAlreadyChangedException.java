package com.company.servicedesk.exceptions;

public class CallRequestAlreadyChangedException extends RuntimeException {
    public CallRequestAlreadyChangedException(String message) {
        super(message);
    }
}
