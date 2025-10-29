package com.carrefour.kata.adapters.dtos;

import com.carrefour.kata.domain.enums.PaymentOption;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponseDto(
        UUID id,
        BigDecimal amount,
        com.carrefour.kata.domain.enums.PaymentOption paymentOption,
        List<InstallmentDto> installments
) {}