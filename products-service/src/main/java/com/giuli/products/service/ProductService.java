package com.giuli.products.service;

import com.giuli.products.client.FakeStoreClient;
import com.giuli.products.dto.*;
import com.giuli.products.entity.Product;
import com.giuli.products.exception.ProductNotFoundException;
import com.giuli.products.exception.StockConflictException;
import com.giuli.products.repository.ProductRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FakeStoreClient fakeStoreClient;

    public ProductService(ProductRepository productRepository, FakeStoreClient fakeStoreClient) {
        this.productRepository = productRepository;
        this.fakeStoreClient = fakeStoreClient;
    }


    // Methods

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> response = new ArrayList<>();

        for (Product product : products) {
            response.add(new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getPrice()
            ));
        }

        return response;
    }

    public Product findProduct(long id) {
        Product foundProduct = productRepository.findById(id).orElse(null);

        if (foundProduct == null) {
            throw new ProductNotFoundException("Product not found with that id");
        }

        return foundProduct;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createProducts() {
        ExternalProductDto[] products = fakeStoreClient.getProducts();

        for (ExternalProductDto p : products) {
            productRepository.save(new Product(p.title(), p.price()));
        }
    }

    public Product updateProduct(long id, ProductDto dto) {
        Product foundProduct = productRepository.findById(id).orElse(null);

        if (foundProduct == null) {
            throw new ProductNotFoundException("Product not found for update");
        }

        foundProduct.setName(dto.name());
        foundProduct.setPrice(dto.price());

        return productRepository.save(foundProduct);
    }

    public void deleteProduct(long id) {
        Product foundProduct = productRepository.findById(id).orElse(null);

        if (foundProduct == null) {
            throw new ProductNotFoundException("Product not found for deletion");
        }

        productRepository.delete(foundProduct);
    }

    public StockResponse decreaseStock(long id, StockRequest request) {

        Product foundProduct = productRepository.findById(id).orElse(null);

        if (foundProduct == null) {
            throw new ProductNotFoundException("Product not found");
        }

        if (request.quantity() <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        if (request.expectedPrice() != foundProduct.getPrice()) {
            throw new StockConflictException("Product price has changed");
        }

        if (request.quantity() > foundProduct.getStock()) {
            throw new StockConflictException("Insufficient stock");
        }

        foundProduct.decreaseStock(request.quantity());

        productRepository.save(foundProduct);

        return new StockResponse(
                foundProduct.getId(),
                foundProduct.getPrice(),
                foundProduct.getStock()
        );
    }
}
