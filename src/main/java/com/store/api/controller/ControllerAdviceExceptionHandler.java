package com.store.api.controller;

import com.store.api.controller.model.ErrorResponse;
import com.store.api.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> generalException(final GeneralException ex) {
        return new ResponseEntity<>(new ErrorResponse(UUID.randomUUID(),
                ex.getErrorMessage(),
                ex.getLocalizedMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> exceptionHandler(final Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(UUID.randomUUID(),
                "something went wrong",
                ex.getLocalizedMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
