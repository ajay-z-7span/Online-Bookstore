package com.demo.onlinebookstore.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundExceptions.class)
    public ResponseEntity<ErrorMessage> resourceNotFound(ResourceNotFoundExceptions e){
        return new ResponseEntity<>(new ErrorMessage("Record Not Found", HttpStatus.NO_CONTENT),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> generalExceptions(Exception e){
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
    }
}
