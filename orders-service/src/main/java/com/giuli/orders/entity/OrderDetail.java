package com.giuli.orders.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;

    private int quantity;

    private double unitPrice;

    // No-argument constructor for JPA
    public OrderDetail() {
    }

    // Constructor for creating details
    public OrderDetail(Long Productid, int quantity, double unitPrice) {
        this.productId = Productid;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Long getProductId() {return productId; }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    // Setter
    public void setOrder(Order order) {
        this.order = order;
    }
}