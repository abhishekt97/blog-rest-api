package com.app.blog.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler{

    //global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalException(Exception e, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> blogAPIExceptionHandler(BlogAPIException e, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundExceptionHandler(ResourceNotFoundException e,
                                                                         WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(CommentNotInPostException.class)
    public ResponseEntity<ErrorDetails> commentNotInPostExceptionHandler(CommentNotInPostException e,
                                                                         WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        WebRequest request){
        ErrorDetails error = new ErrorDetails(new Date(),
                ex.getBindingResult().getAllErrors().stream().
                        map(ObjectError::getDefaultMessage).collect(Collectors.joining(",")),
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false));
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> constraintViolationException(ConstraintViolationException ex,
                                                                     WebRequest request){
        ErrorDetails error = new ErrorDetails(new Date(),
                ex.getConstraintViolations().stream().
                        map(ConstraintViolation::getMessage).collect(Collectors.joining(",")),
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false));
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorDetails> roleNotFoundExceptionHandler(RoleNotFoundException e,
                                                                     WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
