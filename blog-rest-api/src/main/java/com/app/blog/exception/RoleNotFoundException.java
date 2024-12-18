package com.app.blog.exception;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorMsg;

    public RoleNotFoundException(HttpStatus httpStatus, String errorMsg) {
        this.httpStatus = httpStatus;
        this.errorMsg = errorMsg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
