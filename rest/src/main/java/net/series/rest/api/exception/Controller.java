package net.series.rest.api.exception;

import net.series.rest.api.exception.type.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Controller {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> notFoundException(BadRequestException exception) {
        ExceptionResponseModel response = new ExceptionResponseModel(exception.getMessage(), 400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}