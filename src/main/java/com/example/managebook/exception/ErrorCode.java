package com.example.managebook.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    INVALID_PRICE(400, "Price must be at least 0", HttpStatus.BAD_REQUEST),
    INVALID_TITLE(400, "Title can not be null or empty", HttpStatus.BAD_REQUEST),
    INVALID_DATE(400, "Date can not be null or empty", HttpStatus.BAD_REQUEST),
    INVALID_ISBN(400, "INVALID_ISBN can not be null or empty", HttpStatus.BAD_REQUEST),
    DUPLICATE_ISBN(409, "ISBN must be unique", HttpStatus.CONFLICT),
    INVALID_AUTHOR_ID(404, "Author may be null or do not exist", HttpStatus.NOT_FOUND),
    INVALID_BOOK_ID(404, "Book may be null or do not exist", HttpStatus.NOT_FOUND),
    FAIL_TO_DELETE(400, "Failed to delete the book", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(500, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
