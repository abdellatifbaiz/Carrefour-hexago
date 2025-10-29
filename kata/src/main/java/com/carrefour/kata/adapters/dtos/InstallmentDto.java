package com.carrefour.kata.adapters.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InstallmentDto(
        UUID id,
        LocalDate dueDate,
        Boolean status,
        BigDecimal amount
) {}

