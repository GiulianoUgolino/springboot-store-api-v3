package com.giuli.products.dto;

public record StockRequest(
        int quantity,
        double expectedPrice
) {
}