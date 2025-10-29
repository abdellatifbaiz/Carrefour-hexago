package com.carrefour.kata.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Installment {

    private UUID id;
    private LocalDate dueDate;
    private boolean status;
    private BigDecimal amount;
    private Order order;
}
