package com.carrefour.kata.domain.port.out;

import com.carrefour.kata.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface OrderRepository {

    Order findOrder(UUID orderid);
    Order updateOrder(Order order);
    Page<Order> findOrders(Pageable pageable);

}
