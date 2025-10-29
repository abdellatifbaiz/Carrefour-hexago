package com.carrefour.kata.adapters.in;


import com.carrefour.kata.adapters.dtos.ErrorResponseDto;
import com.carrefour.kata.exceptions.InvalidPaymentOptionException;
import com.carrefour.kata.exceptions.OrderExceedAmount;
import com.carrefour.kata.exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderExceedAmount.class)
    public ResponseEntity<ErrorResponseDto> handleOrderExceedAmount(OrderExceedAmount orderExceedAmount) {
        return new ResponseEntity<>(new ErrorResponseDto(orderExceedAmount.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPaymentOptionException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPaymentOptionException(InvalidPaymentOptionException invalidPaymentOptionException) {
        return new ResponseEntity<>(new ErrorResponseDto(invalidPaymentOptionException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotFoundException(OrderNotFoundException orderNotFoundException) {
        return new ResponseEntity<>(new ErrorResponseDto(orderNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }
}
