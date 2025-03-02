package com.stas_kozh.error;

public class NonretryableException extends RuntimeException {

    public NonretryableException(String message) {
        super(message);
    }

    public NonretryableException(Throwable cause) {
        super(cause);
    }
}
