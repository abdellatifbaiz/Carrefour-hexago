package com.carrefour.kata.adapters.entities;

import com.carrefour.kata.domain.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "installment")
public class InstallmentEntity {
    @Id
    private UUID id;

    private LocalDate dueDate;

    private boolean status;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
