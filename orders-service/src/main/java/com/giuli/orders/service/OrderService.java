package com.giuli.orders.service;


import com.giuli.orders.client.ProductsClient;
import com.giuli.orders.dto.*;
import com.giuli.orders.entity.Order;
import com.giuli.orders.entity.OrderDetail;
import com.giuli.orders.exception.InvalidQuantityException;
import com.giuli.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductsClient productsClient;


    // Dependency injection
    public OrderService(OrderRepository orderRepository, ProductsClient productsClient) {
        this.orderRepository = orderRepository;
        this.productsClient = productsClient;
    }

    // Create and save an order with the response we prepared in the class
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Create the order to assign its details first and then save it
        Order order = new Order();

        // Iterate over orderRequest to build OrderDetail objects
        for (OrderDetailRequest orderDetailRequest : orderRequest.details()) {

            // Check that no negative product quantity was entered
            if (orderDetailRequest.quantity() <= 0) {
                throw new InvalidQuantityException("Negative product quantity");
            }

            // Create a StockRequest to send products-service
            StockRequest stockRequest = new StockRequest(orderDetailRequest.quantity(),
                    orderDetailRequest.expectedPrice());

            // Request a stock decrease; an HTTP error may occur here
            StockResponse stockResponse = productsClient.decreaseStock(orderDetailRequest.productId(), stockRequest);

            // Create an order detail using the confirmed product price
            OrderDetail orderDetail = new OrderDetail(
                    stockResponse.productId(),
                    orderDetailRequest.quantity(),
                    stockResponse.unitPrice()
            );

            // Add the detail to the order
            order.addDetail(orderDetail);
        }

        // The order is ready, now save it -> the id is generated here because it was sent to the database
        Order savedOrder = orderRepository.save(order); // save returns the entity
        // Now use it to create the OrderResponse
        return new OrderResponse(savedOrder.getId(), savedOrder.getDate(), order.calculateTotal());
    }
}
