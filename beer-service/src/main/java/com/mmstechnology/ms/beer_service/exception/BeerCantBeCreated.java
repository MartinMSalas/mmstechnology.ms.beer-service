package com.mmstechnology.ms.beer_service.exception;

public class BeerCantBeCreated extends RuntimeException {
    public BeerCantBeCreated(String message) {
        super(message);
    }
}
