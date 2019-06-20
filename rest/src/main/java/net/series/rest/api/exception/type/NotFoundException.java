package net.series.rest.api.exception.type;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NotFoundException() {

    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}