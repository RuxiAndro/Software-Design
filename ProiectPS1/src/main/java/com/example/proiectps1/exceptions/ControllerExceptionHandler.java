package com.example.proiectps1.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

//Controller de configurare pentru error handling
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@ControllerAdvice
@Log4j2
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    //Metoda de handling specifica pentru tratarea exceptiei definite de noi (ApiExceptionResponse)
    //Daca mai adaugam alte exceptii, mai punem o @ExceptionHandle(value = "nume clasa de exceptii creata de noi".class)
    @ExceptionHandler(value = ApiExceptionResponse.class)
    protected ResponseEntity<Object> handleApiExceptionResponse(ApiExceptionResponse ex) {
        HttpStatus status = ex.getStatus() != null ? ex.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        return responseEntityBuilder(ApiExceptionResponse.builder()
                .errors(ex.getErrors())
                .status(status)
                .message(ex.getMessage())
                .build());
    }


    @ExceptionHandler(value = Throwable.class)
    protected ResponseEntity<Object> handleGeneric(Throwable ex) {
        HttpStatus status =  HttpStatus.INTERNAL_SERVER_ERROR;
        String message = Arrays.toString(ex.getStackTrace());
        log.error(message);
        return responseEntityBuilder(ApiExceptionResponse.builder()
                .status(status)
                .message(ex.getMessage() + ": " + message)
                .build());
    }

    private ResponseEntity<Object> responseEntityBuilder(ApiExceptionResponse ex) {
        return new ResponseEntity<>(ex, ex.getStatus());
    }
}