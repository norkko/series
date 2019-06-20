package net.series.rest.api.exception;

import lombok.Data;

@Data
class Exception {

    private String error;
    private int errorCode;

    Exception(String error, int errorCode) {
        this.error = error;
        this.errorCode = errorCode;
    }

}
