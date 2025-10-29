package com.carrefour.kata.domain.port.in;

import com.carrefour.kata.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GetAllOrdersCase {
    Page<Order> getAllOrders(Pageable pageable);
}
