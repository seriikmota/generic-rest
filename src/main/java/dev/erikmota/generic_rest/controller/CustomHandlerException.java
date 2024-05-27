package dev.erikmota.generic_rest.controller;

import dev.erikmota.generic_rest.exceptions.DataException;
import dev.erikmota.generic_rest.exceptions.ParameterRequiredException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomHandlerException {

    @ExceptionHandler(DataException.class)
    public ResponseEntity<String> handleDataException(DataException ex){
        ex.printStackTrace();
        return ResponseEntity.status(ex.getError().getId()).body(ex.getMessage());
    }

    @ExceptionHandler(ParameterRequiredException.class)
    public ResponseEntity<String> handleParameterRequiredException(ParameterRequiredException ex){
        ex.printStackTrace();
        return ResponseEntity.status(ex.getError().getId()).body(ex.getMessage());
    }
}
