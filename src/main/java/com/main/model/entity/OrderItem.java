package com.main.model.entity;

import com.main.infrastructure.generic.model.entity.GenericEntity;
import com.main.model.enums.OrderItemStatus; // PENDING, PREPARING, DELIVERED, CANCELED
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "customer_name", length = 50)
    private String customerName;

    @Column(length = 255)
    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderItemStatus status = OrderItemStatus.PENDING;

    public BigDecimal getTotalPrice() {
        if (quantity == null || unitPrice == null) return BigDecimal.ZERO;
        return unitPrice.multiply(quantity);
    }

    public java.util.UUID getPrimaryProductId() {
        return this.product != null && this.product.getPrimaryProduct() != null
                ? this.product.getPrimaryProduct().getId()
                : null;
    }
}