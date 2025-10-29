package com.carrefour.kata.exceptions;

public class InvalidPaymentOptionException extends BusinessException {
    public InvalidPaymentOptionException(String message) {
        super(message);
    }
}