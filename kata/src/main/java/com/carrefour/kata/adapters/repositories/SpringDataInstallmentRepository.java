package com.carrefour.kata.adapters.repositories;

import com.carrefour.kata.adapters.entities.InstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataInstallmentRepository extends JpaRepository<InstallmentEntity, UUID> {
}
