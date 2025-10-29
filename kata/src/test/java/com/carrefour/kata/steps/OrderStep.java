package com.carrefour.kata.steps;


import com.carrefour.kata.domain.consts.PaymentProperties;
import com.carrefour.kata.domain.enums.PaymentOption;
import com.carrefour.kata.domain.model.Installment;
import com.carrefour.kata.domain.model.Order;
import com.carrefour.kata.domain.port.out.OrderRepository;
import com.carrefour.kata.domain.service.OrderSerevice;
import io.cucumber.java.en.*;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;


public class OrderStep {

    private OrderSerevice orderService;
    private Order order;
    private Exception exception;

    @Given("une commande avec un montant de {double} et une option de paiement {string}")
    public void une_commande_avec_un_montant_et_une_option(double amount, String paymentOption) {
        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderSerevice(orderRepository);
        order = Order.builder()
                .amount(BigDecimal.valueOf(amount))
                .paymentOption(PaymentOption.valueOf(paymentOption))
                .build();
    }

    @When("je génère les échéances de la commande")
    public void je_genere_les_echeances() {
        try {
            order = orderService.generateOrderInstallenemt(order);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("la commande doit contenir {int} échéances")
    public void la_commande_doit_contenir_echeances(Integer expectedCount) {
        Assertions.assertThat(order.getInstallments()).hasSize(expectedCount);
    }

    @Then("chaque échéance doit avoir le même montant")
    public void chaque_echeance_doit_avoir_le_meme_montant() {
        BigDecimal firstAmount = order.getInstallments().get(0).getAmount();
        Assertions.assertThat(order.getInstallments())
                .extracting(Installment::getAmount)
                .allMatch(amount -> amount.compareTo(firstAmount) == 0);
    }

    @Then("chaque échéance doit être espacée d'un mois")
    public void chaque_echeance_doit_etre_espacee_dun_mois() {
        for (int i = 1; i < order.getInstallments().size(); i++) {
            LocalDate prev = order.getInstallments().get(i - 1).getDueDate();
            LocalDate current = order.getInstallments().get(i).getDueDate();
            Assertions.assertThat(current).isEqualTo(prev.plusMonths(1));
        }
    }

    @Then("le montant doit inclure les frais bancaires")
    public void le_montant_doit_inclure_les_frais_bancaires() {
        BigDecimal expected = order.getAmount().add(PaymentProperties.BANK_FEE).setScale(2, RoundingMode.HALF_UP);
        Assertions.assertThat(order.getInstallments().get(0).getAmount()).isEqualByComparingTo(expected);
    }

    @Then("une erreur {string} doit être levée")
    public void une_erreur_doit_etre_levee(String exceptionName) {
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass().getSimpleName()).isEqualTo(exceptionName);
    }
}

