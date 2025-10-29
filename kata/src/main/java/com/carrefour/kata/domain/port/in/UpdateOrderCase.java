package com.carrefour.kata.domain.port.in;

import com.carrefour.kata.domain.model.Order;

public interface UpdateOrderCase {

    public Order updateOrderInstallenemt(Order order);
}
