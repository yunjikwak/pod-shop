package com.example.demo.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ExceptionType type;

    public CustomException(ExceptionType type, Object additional) {
        super(type.getMessage() + additional.toString());
        this.type = type;
    }
}
