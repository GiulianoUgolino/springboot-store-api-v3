package com.giuli.products.dto;

public record StockResponse(
        long productId,
        double unitPrice,
        int remainingStock
) {
}