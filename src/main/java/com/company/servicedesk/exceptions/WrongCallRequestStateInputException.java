package com.company.servicedesk.exceptions;

public class WrongCallRequestStateInputException extends RuntimeException {
    public WrongCallRequestStateInputException(String message) {
        super(message);
    }
}
