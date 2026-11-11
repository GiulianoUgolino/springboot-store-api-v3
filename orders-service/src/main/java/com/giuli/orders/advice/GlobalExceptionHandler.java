package com.giuli.orders.advice;

import com.giuli.orders.exception.InvalidQuantityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidQuantityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidQuantity(
            InvalidQuantityException ex) {

        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.Conflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleStockConflict(
            HttpClientErrorException.Conflict ex) {

        Map<?, ?> error = ex.getResponseBodyAs(Map.class);

        return Map.of("message", (String) error.get("message"));
    }
}