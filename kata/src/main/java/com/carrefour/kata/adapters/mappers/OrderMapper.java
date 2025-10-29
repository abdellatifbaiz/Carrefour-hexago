package com.carrefour.kata.adapters.mappers;


import com.carrefour.kata.adapters.dtos.InstallmentDto;
import com.carrefour.kata.adapters.dtos.OrderResponseDto;
import com.carrefour.kata.adapters.entities.InstallmentEntity;
import com.carrefour.kata.adapters.entities.OrderEntity;
import com.carrefour.kata.domain.model.Installment;
import com.carrefour.kata.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponseDto toDto(Order order);

    Order fromDto(OrderResponseDto dto);

    List<InstallmentDto> toInstallmentDtos(List<Installment> installments);

    InstallmentDto toInstallmentDto(Installment installment);

    @Mapping(target = "order", ignore = true)
    InstallmentEntity fromInstallment(Installment installment);

    @Mapping(target = "order", ignore = true)
    Installment fromInstallmentEntity(InstallmentEntity installment);

    OrderEntity toEntity(Order order);
    Order fromEntity(OrderEntity entity);

}

