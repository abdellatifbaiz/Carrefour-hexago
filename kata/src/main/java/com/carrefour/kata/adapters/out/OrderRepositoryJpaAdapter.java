package com.carrefour.kata.adapters.out;

import com.carrefour.kata.adapters.entities.InstallmentEntity;
import com.carrefour.kata.adapters.entities.OrderEntity;
import com.carrefour.kata.adapters.mappers.OrderMapper;
import com.carrefour.kata.adapters.repositories.SpringDataOrderRepository;
import com.carrefour.kata.domain.model.Order;
import com.carrefour.kata.domain.port.out.OrderRepository;
import com.carrefour.kata.exceptions.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryJpaAdapter implements OrderRepository {

    private final SpringDataOrderRepository  springDataOrderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order findOrder(UUID orderid) {
        OrderEntity orderEntity =  springDataOrderRepository.findById(orderid).orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'identifiant " + orderid));
        return orderMapper.fromEntity(orderEntity);
    }

    @Override
    public Order updateOrder(Order order) {
        OrderEntity existingOrder =   springDataOrderRepository.findById(order.getId()).orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'identifiant " + order.getId()));
        existingOrder.setAmount(order.getAmount());
        existingOrder.setPaymentOption(order.getPaymentOption());
        List<InstallmentEntity> installmentEntity = order.getInstallments().stream().map(orderMapper::fromInstallment).collect(Collectors.toList());
        existingOrder.setInstallments(installmentEntity);
        existingOrder.getInstallments().forEach(i -> i.setOrder(existingOrder));
        existingOrder.setInstallments(installmentEntity);
        return orderMapper.fromEntity(springDataOrderRepository.save(existingOrder));
    }

    @Override
    @Cacheable()
    public Page<Order> findOrders(Pageable pageable) {
        Page<OrderEntity> orderEntity = springDataOrderRepository.findAll(pageable);
        return orderEntity.map(orderMapper::fromEntity);
    }

}
