package net.series.rest.api.exception.type;

import lombok.Data;

@Data
public class UsernameAlreadyExistException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyExistException() {

    }

    public UsernameAlreadyExistException(String message) {
        super(message);
    }

    public UsernameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

}