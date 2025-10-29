package com.carrefour.kata.adapters.entities;

import com.carrefour.kata.domain.enums.PaymentOption;
import jakarta.persistence.*;
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
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    private UUID id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private PaymentOption paymentOption;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<InstallmentEntity> installments;
}
