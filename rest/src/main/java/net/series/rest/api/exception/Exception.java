package net.series.rest.api.exception;

import lombok.Data;

@Data
public class Exception {

    private String error;
    private int errorCode;

    public Exception() {

    }

    public Exception(String error, int errorCode) {
        this.error = error;
        this.errorCode = errorCode;
    }

}
