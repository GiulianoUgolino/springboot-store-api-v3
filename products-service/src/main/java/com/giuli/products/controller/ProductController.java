package com.giuli.products.controller;

import com.giuli.products.dto.ProductDto;
import com.giuli.products.dto.ProductResponse;
import com.giuli.products.dto.StockRequest;
import com.giuli.products.dto.StockResponse;
import com.giuli.products.entity.Product;
import com.giuli.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getProducts() {return productService.getAllProducts(); }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") long id) {
        return productService.findProduct(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") long id, @RequestBody ProductDto dto) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id")long id) {
        productService.deleteProduct(id);
    }

    // Interaction with orders-service -> // Endpoint used by orders-service to decrease product stock
    @PostMapping("/{id}/decrease-stock")
    public StockResponse decreaseStock(
            @PathVariable Long id,
            @RequestBody StockRequest request) {

        return productService.decreaseStock(id, request);
    }

}
