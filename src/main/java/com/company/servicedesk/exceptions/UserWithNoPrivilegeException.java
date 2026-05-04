package com.company.servicedesk.exceptions;

public class UserWithNoPrivilegeException extends RuntimeException {
    public UserWithNoPrivilegeException(String message) {
        super(message);
    }
}
