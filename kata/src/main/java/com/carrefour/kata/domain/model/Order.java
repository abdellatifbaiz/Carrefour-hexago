package com.carrefour.kata.domain.model;

import com.carrefour.kata.domain.enums.PaymentOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private UUID id;

    private BigDecimal amount;

    private PaymentOption paymentOption;

    private List<Installment> installments;
}
