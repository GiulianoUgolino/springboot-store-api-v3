package com.giuli.products.client;

import com.giuli.products.dto.ExternalProductDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class FakeStoreClient {

    private final RestClient restClient;

    // Spring injects a RestClient.Builder instance.
    // We configure the base URL and use build() to create a RestClient object.
    // Finally, we store the created object in the restClient field.
    public FakeStoreClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://fakestoreapi.com")
                .build();
    }

    public ExternalProductDto[] getProducts() {
        return restClient.get()
                .uri("/products") // URI path of the products resource
                .retrieve()
                .body(ExternalProductDto[].class);
    }
}