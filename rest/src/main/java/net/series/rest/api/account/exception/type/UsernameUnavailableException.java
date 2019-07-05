package net.series.rest.api.account.exception.type;

public class UsernameUnavailableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsernameUnavailableException() {}

    public UsernameUnavailableException(String message) {
        super(message);
    }

    public UsernameUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
