package net.series.rest.api.exception.controller;

import net.series.rest.api.exception.Exception;
import net.series.rest.api.exception.type.NotFoundException;
import net.series.rest.api.exception.type.UsernameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException exception) {
        Exception response = new net.series.rest.api.exception.Exception(exception.getMessage(), 404);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UsernameAlreadyExistException.class)
    public ResponseEntity<Object> usernameAlreadyExistException(UsernameAlreadyExistException exception) {
        Exception response = new Exception(exception.getMessage(), 409);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
