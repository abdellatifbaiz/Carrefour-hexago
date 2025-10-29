package com.carrefour.kata.domain.service;

import com.carrefour.kata.domain.consts.PaymentProperties;
import com.carrefour.kata.domain.enums.PaymentOption;
import com.carrefour.kata.domain.model.Installment;
import com.carrefour.kata.domain.model.Order;
import com.carrefour.kata.domain.port.in.GenerateOrderInstallenemtCase;
import com.carrefour.kata.domain.port.in.GetAllOrdersCase;
import com.carrefour.kata.domain.port.in.UpdateOrderCase;
import com.carrefour.kata.domain.port.out.OrderRepository;
import com.carrefour.kata.exceptions.OrderExceedAmount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderSerevice implements GenerateOrderInstallenemtCase, UpdateOrderCase, GetAllOrdersCase {


    private final OrderRepository orderRepository;
    public OrderSerevice(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public Order generateOrderInstallenemt(Order order) {


        PaymentOption paymentOption = order.getPaymentOption();
        validateOrder(order);
        List<Installment> installments = switch (paymentOption) {
            case BANK_TRANSFER -> generateBankTransferInstallment(order);
            case THREE_TIMES_NO_FEES -> generateThreeInstallments(order);
            case FOUR_TIMES_WITH_FEES -> generateFourInstallmentsWithFees(order);
        };

        order.setInstallments(installments);
        installments.forEach(i -> i.setOrder(order));
        return order;

    }

    @Override
    public Order updateOrderInstallenemt(Order order) {
        return orderRepository.updateOrder(order);
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findOrders(pageable);
    }

    private void validateOrder(Order order) {
        if (order.getAmount().compareTo(PaymentProperties.MAX_AMOUNT) < 0) {
            throw new OrderExceedAmount("The amount must be greater than " + PaymentProperties.MAX_AMOUNT);
        }
    }

    private List<Installment> generateBankTransferInstallment(Order order) {
        BigDecimal amount = order.getAmount();
        BigDecimal total = amount.add(PaymentProperties.BANK_FEE).setScale(2, RoundingMode.HALF_UP);
        LocalDate now = LocalDate.now();
        List<Installment> installments;
        Installment installment  = Installment.builder()
                .id(UUID.randomUUID())
                .dueDate(now)
                .amount(total)
                .build();
        installments =  List.of(installment);
        return installments;

    }

    private List<Installment> generateFourInstallmentsWithFees(Order order) {
        BigDecimal amount = order.getAmount();
        BigDecimal total = amount.multiply(PaymentProperties.PAYMENT_FEE_AMOUNT).setScale(2, RoundingMode.HALF_UP);
        LocalDate now = LocalDate.now();
        List<Installment> installments = new ArrayList<>();

        BigDecimal part = total.divide(BigDecimal.valueOf(4), 2, RoundingMode.HALF_UP);
        for (int i = 0; i < 4; i++) {
            Installment installment  = Installment.builder()
                    .id(UUID.randomUUID())
                    .dueDate(now)
                    .amount(part)
                    .build();
            installments.add(installment);
            now = now.plusDays(1);
        }
        return installments;
    }

    private List<Installment> generateThreeInstallments(Order order) {
        BigDecimal amount = order.getAmount();
        LocalDate now = LocalDate.now();
        BigDecimal part = amount.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);
        List<Installment> installments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Installment installment = Installment.builder()
                    .id(UUID.randomUUID())
                    .dueDate(now)
                    .amount(part)
                    .build();
            installments.add(installment);
            now = now.plusMonths(1);
        }
        return installments;
    }
}
