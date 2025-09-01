package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandle {

//    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
//    public ResponseEntity<ErrorResponse> handle(ChangeSetPersister.NotFoundException e) {
//        String message = e.getMessage();
//        log.warn(message, e);
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(ErrorResponse.failure(message));
//    }

    // custom exception handler
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handle(CustomException e) {
        String message = e.getMessage();
        log.warn(message, e);
        return ResponseEntity
                .status(e.getType().getStatus())
                .body(ErrorResponse.failure(message));
    }

    // @Valid error
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn(message, e);
        return ResponseEntity
                .status(400)
                .body(ErrorResponse.failure(message));
    }

//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handle(HandlerMethodValidationException e) {
        String message = e.getMessage();
        log.warn(message, e);
        return ResponseEntity
                .status(400)
                .body(ErrorResponse.failure(message));
    }

    // other
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        String message = e.getMessage();
        log.warn(message, e);
        return ResponseEntity
                .status(500)
                .body(ErrorResponse.failure(ExceptionType.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
