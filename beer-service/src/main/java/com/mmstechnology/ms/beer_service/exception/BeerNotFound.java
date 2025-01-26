package com.mmstechnology.ms.beer_service.exception;


public class BeerNotFound extends RuntimeException {
    public BeerNotFound(String message) {
        super(message);
    }
}
