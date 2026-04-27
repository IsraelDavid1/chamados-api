package com.company.servicedesk.exceptions;

public class AlreadyFinishedCallException extends RuntimeException{
    public AlreadyFinishedCallException(String message) { super(message);}
}
