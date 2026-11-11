package com.giuli.orders.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    private List<OrderDetail> details = new ArrayList<>();

    // No-argument constructor
    public Order() {
        this.date = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    // Add a detail to the order
    public void addDetail(OrderDetail detail) {
        details.add(detail);
        detail.setOrder(this);
    }

    // Calculate the order total
    public double calculateTotal() {
        double total = 0;

        for (OrderDetail detail : details) {
            total += detail.getQuantity() * detail.getUnitPrice();
        }

        return total;
    }
}