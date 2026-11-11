package com.giuli.orders.client;

import com.giuli.orders.dto.StockRequest;
import com.giuli.orders.dto.StockResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductsClient {

    private final RestClient restClient;

    public ProductsClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    public StockResponse decreaseStock(Long productId, StockRequest request) {

        return restClient.post()
                .uri("/products/{id}/decrease-stock", productId)
                .body(request)
                .retrieve()
                .body(StockResponse.class);
    }
}