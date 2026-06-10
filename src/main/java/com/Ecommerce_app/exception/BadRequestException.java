package com.Ecommerce_app.exception;

public class BadRequestException
        extends RuntimeException {

    public BadRequestException(
            String message) {

        super(message);
    }
}
