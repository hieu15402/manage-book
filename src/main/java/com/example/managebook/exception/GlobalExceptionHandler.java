package com.example.managebook.exception;

import com.example.managebook.dto.response.general.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = InvalidArgumentException.class)
    public ResponseEntity<GenericResponse<?>> handleInvalidArgumentException(InvalidArgumentException ex) {
        log.error(ex.getMessage(), ex);
        ErrorCode errorCode = ex.getErrorCode();

        GenericResponse<?> response = GenericResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<GenericResponse<?>> handleAppException(AppException ex) {
        log.error(ex.getMessage(), ex);
        ErrorCode errorCode = ex.getErrorCode();

        GenericResponse<?> response = GenericResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
    }
}
