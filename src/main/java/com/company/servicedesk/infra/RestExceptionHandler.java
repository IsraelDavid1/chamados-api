package com.company.servicedesk.infra;

import com.company.servicedesk.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AlreadyFinishedCallException.class)
    public ResponseEntity<RestErrorMessage> alreadyFinishedCallHandler(AlreadyFinishedCallException exception,
                                                                       HttpServletRequest request) {
        RestErrorMessage errorResponse = new RestErrorMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
                "BAD_REQUEST", exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CallNotFoundException.class)
    public ResponseEntity<RestErrorMessage> callNotFoundHandler(CallNotFoundException exception,
                                                                HttpServletRequest request) {
        RestErrorMessage errorResponse = new RestErrorMessage(LocalDateTime.now(), HttpStatus.NOT_FOUND,
                "NOT_FOUND", exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException exception,
                                                                HttpServletRequest request) {
        RestErrorMessage errorResponse = new RestErrorMessage(LocalDateTime.now(), HttpStatus.NOT_FOUND,
                "NOT_FOUND", exception.getMessage(), request.getRequestURI());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserWithNoPrivilegeException.class)
    public ResponseEntity<RestErrorMessage> userWithNoPrivilegeHandler(UserWithNoPrivilegeException exception,
                                                                       HttpServletRequest request) {
        RestErrorMessage errorResponse = new RestErrorMessage(LocalDateTime.now(), HttpStatus.FORBIDDEN,
                "FORBIDDEN", exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(InvalidOrExpiredTokenException.class)
    public ResponseEntity<RestErrorMessage> invalidOrExpiredTokenHandler(InvalidOrExpiredTokenException exception,
                                                                         HttpServletRequest request) {
        RestErrorMessage errorResponse = new RestErrorMessage(LocalDateTime.now(), HttpStatus.UNAUTHORIZED,
                "UNAUTHORIZED", exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<RestErrorMessage> userAlreadyExistsHandler(UserAlreadyExistsException exception,
                                                                     HttpServletRequest request) {
        RestErrorMessage errorResponse = new RestErrorMessage(LocalDateTime.now(), HttpStatus.CONFLICT,
                "CONFLICT", exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<RestErrorMessage> tokenGenerationHandler(TokenGenerationException exception,
                                                                   HttpServletRequest request) {
        RestErrorMessage errorResponse = new RestErrorMessage(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR", exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
