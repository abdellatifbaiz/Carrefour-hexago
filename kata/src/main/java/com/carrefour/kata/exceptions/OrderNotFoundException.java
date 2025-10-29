package com.carrefour.kata.exceptions;

public class OrderNotFoundException extends BusinessException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
