package io.ljunggren.tracking.webservice.exception;

public class BadRequestException extends Exception {

    private static final long serialVersionUID = 3758891259496067516L;

    public BadRequestException(String message) {
        super(message);
    }

}
