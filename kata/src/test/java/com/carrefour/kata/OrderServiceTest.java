package com.carrefour.kata;

import com.carrefour.kata.domain.enums.PaymentOption;
import com.carrefour.kata.domain.model.Order;
import com.carrefour.kata.domain.port.out.OrderRepository;
import com.carrefour.kata.domain.service.OrderSerevice;
import com.carrefour.kata.exceptions.OrderExceedAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
public class OrderServiceTest {

    private OrderRepository orderRepository;
    private OrderSerevice orderSerevice;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        orderSerevice = new OrderSerevice(orderRepository);
    }

    @Test
    public void  generateOrderInstallenemt_should_return_exception()
    {
        Order order = Order.builder().amount(BigDecimal.valueOf(100))
                .paymentOption(PaymentOption.THREE_TIMES_NO_FEES)
                .build();
        assertThrows(OrderExceedAmount.class,
                () -> orderSerevice.generateOrderInstallenemt(order));
    }

    @Test
    public void  generateOrderInstallenemt_should_return_order_with_three()
    {
        Order order = Order.builder().amount(BigDecimal.valueOf(1000))
                .paymentOption(PaymentOption.THREE_TIMES_NO_FEES)
                .build();
        Order resOrder = orderSerevice.generateOrderInstallenemt(order);
        assertThat(resOrder.getInstallments().size()).isEqualTo(3);
    }

    @Test
    public void  generateOrderInstallenemt_should_return_order_with_four()
    {
        Order order = Order.builder().amount(BigDecimal.valueOf(1000))
                .paymentOption(PaymentOption.FOUR_TIMES_WITH_FEES)
                .build();
        Order resOrder = orderSerevice.generateOrderInstallenemt(order);
        assertThat(resOrder.getInstallments().size()).isEqualTo(4);
    }

    @Test
    public void  generateOrderInstallenemt_should_return_order_with_one()
    {
        Order order = Order.builder().amount(BigDecimal.valueOf(1000))
                .paymentOption(PaymentOption.BANK_TRANSFER)
                .build();
        Order resOrder = orderSerevice.generateOrderInstallenemt(order);
        assertThat(resOrder.getInstallments().size()).isEqualTo(1);
    }

    @Test
    public void getAllOrders_should_return_all_orders() {
        Order order = Order.builder().amount(BigDecimal.valueOf(1000))
                .paymentOption(PaymentOption.BANK_TRANSFER)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        List<Order> orders = List.of(order);
        Page<Order> pageOfOrders = new PageImpl<>(orders, pageable, orders.size());
        when(orderRepository.findOrders(pageable)).thenReturn(pageOfOrders);

        Page<Order> result = orderSerevice.getAllOrders(pageable);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getAmount()).isEqualByComparingTo("1000");
        assertThat(result.getContent().get(0).getPaymentOption()).isEqualTo(PaymentOption.BANK_TRANSFER);

        verify(orderRepository, times(1)).findOrders(pageable);
    }


}
