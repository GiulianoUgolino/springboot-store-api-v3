package com.giuli.orders.dto;

public record StockResponse(long productId, double unitPrice, int remainingStock) {
}
