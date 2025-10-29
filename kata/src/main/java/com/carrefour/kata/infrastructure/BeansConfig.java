package com.carrefour.kata.infrastructure;


import com.carrefour.kata.domain.port.out.OrderRepository;
import com.carrefour.kata.domain.service.OrderSerevice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public OrderSerevice createOrderServiceBean(OrderRepository orderRepository)
    {
        return new OrderSerevice(orderRepository);
    }
}
