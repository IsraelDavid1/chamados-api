package com.company.servicedesk.infra;

import com.company.servicedesk.exceptions.AlreadyFinishedCallException;
import com.company.servicedesk.exceptions.CallNotFoundException;
import com.company.servicedesk.exceptions.UserNotFoundException;
import com.company.servicedesk.exceptions.UserWithNoPrivilegeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AlreadyFinishedCallException.class)
    public ResponseEntity<RestErrorMessage> alreadyFinishedCallHandler(AlreadyFinishedCallException exception) {
        RestErrorMessage errorResponse = new RestErrorMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
                "BAD_REQUEST", exception.getMessage(), "/call");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CallNotFoundException.class)
    public ResponseEntity<RestErrorMessage> callNotFoundHandler(CallNotFoundException exception) {
        RestErrorMessage errorResponse = new RestErrorMessage(LocalDateTime.now(), HttpStatus.NOT_FOUND,
                "NOT_FOUND", exception.getMessage(), "/call");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException exception) {
        RestErrorMessage errorResponse = new RestErrorMessage(LocalDateTime.now(), HttpStatus.NOT_FOUND,
                "NOT_FOUND", exception.getMessage(), "/call");
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserWithNoPrivilegeException.class)
    public ResponseEntity<RestErrorMessage> userWithNoPrivilegeHandler(UserWithNoPrivilegeException exception) {
        RestErrorMessage errorResponse = new RestErrorMessage(LocalDateTime.now(), HttpStatus.FORBIDDEN,
                "FORBIDDEN", exception.getMessage(), "/call");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
