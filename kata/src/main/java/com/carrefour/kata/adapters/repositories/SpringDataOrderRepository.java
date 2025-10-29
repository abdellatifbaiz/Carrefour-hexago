package com.carrefour.kata.adapters.repositories;

import com.carrefour.kata.adapters.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, UUID> {
}
