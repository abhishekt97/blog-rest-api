package com.app.blog.exception;

import org.springframework.http.HttpStatus;

public class CommentNotInPostException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final String message;

    public CommentNotInPostException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
