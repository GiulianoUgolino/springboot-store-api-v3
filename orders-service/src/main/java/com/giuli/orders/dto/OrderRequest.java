package com.giuli.orders.dto;

import java.util.List;

public record OrderRequest(
        List<OrderDetailRequest> details
) {
}
