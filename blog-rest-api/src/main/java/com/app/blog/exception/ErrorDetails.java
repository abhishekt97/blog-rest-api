package com.app.blog.exception;

import java.util.Date;

public record ErrorDetails(Date timestamp, String message, int errorCode, String details) {
}
