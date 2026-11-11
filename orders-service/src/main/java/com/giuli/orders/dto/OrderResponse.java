package com.giuli.orders.dto;

// In this class we control the data the client receives as a response after
// an order is created

import java.time.LocalDateTime;

public record OrderResponse(
        Long id, // here we can use a primitive
        LocalDateTime date,
        double total
) {
}
