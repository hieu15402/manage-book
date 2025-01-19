package com.example.managebook.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvalidArgumentException extends RuntimeException {

    private ErrorCode errorCode;

    public InvalidArgumentException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
