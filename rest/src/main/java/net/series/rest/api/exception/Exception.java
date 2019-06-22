package net.series.rest.api.exception;

import lombok.Data;

@Data
public class Exception {

    private String error;

    private int status;

    public Exception() {

    }

    public Exception(String error, int status) {
        this.error = error;
        this.status = status;
    }

}
