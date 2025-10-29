package com.carrefour.kata.exceptions;

public class OrderExceedAmount extends BusinessException {
    public OrderExceedAmount(String message) {
        super(message);
    }
}
