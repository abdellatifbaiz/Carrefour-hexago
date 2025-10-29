package com.carrefour.kata.domain.enums;

import com.carrefour.kata.exceptions.InvalidPaymentOptionException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentOption {
    THREE_TIMES_NO_FEES,
    FOUR_TIMES_WITH_FEES,
    BANK_TRANSFER;

    @JsonCreator
    public static PaymentOption fromValue(String value) {
        try {
            return PaymentOption.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidPaymentOptionException("Option non supportée : " + value);
        }
    }
}
