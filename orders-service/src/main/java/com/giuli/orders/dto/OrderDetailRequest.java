package com.giuli.orders.dto;

public record OrderDetailRequest(Long productId, int quantity, double expectedPrice) {
}
