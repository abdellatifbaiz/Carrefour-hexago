package com.carrefour.kata;


import com.carrefour.kata.adapters.dtos.OrderResponseDto;
import com.carrefour.kata.domain.enums.PaymentOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTest {

    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setup(){
        baseUrl = baseUrl + ":" + port;
    }

    @Test
    public void should_generate_order_with_installments(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        OrderResponseDto order = new OrderResponseDto(UUID.randomUUID(), new BigDecimal("1500"), PaymentOption.THREE_TIMES_NO_FEES,null);

        HttpEntity<OrderResponseDto> requestEntity = new HttpEntity<>(order, headers);

        ResponseEntity<com.carrefour.kata.adapters.dtos.OrderResponseDto> responseEntity =
                restTemplate.postForEntity(baseUrl+"/api/orders/generate", requestEntity, OrderResponseDto.class);
        OrderResponseDto response = responseEntity.getBody();

        assertThat(response.installments().size()).isEqualTo(3);
    }






}
