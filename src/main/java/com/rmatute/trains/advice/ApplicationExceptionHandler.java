package com.rmatute.trains.advice;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rmatute.trains.utils.GenericException;
import com.rmatute.trains.utils.GenericResponse;

@ControllerAdvice
public class ApplicationExceptionHandler {
	
	@ExceptionHandler(GenericException.class)
    public ResponseEntity<GenericResponse<Object>> handlerGenericException(GenericException genericException){

        return ResponseEntity
                .status(genericException.getStatus().getStatusCode())
                .body(
                        new GenericResponse<>()
                                .message(genericException.getMessage())
                                .success(genericException.getSuccess())
                                .errorCode(!genericException.getSuccess() ? genericException.hashCode() : null)
                                .data(genericException.getData())
                );
    }


}
