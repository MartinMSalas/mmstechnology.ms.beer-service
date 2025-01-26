package com.mmstechnology.ms.beer_service.exception;

public class CustomerNotFound extends RuntimeException {
    public CustomerNotFound(String message) {
        super(message);
    }
}
