package com.rotciv.order.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_details")
@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor
@IdClass(OrderDetailCompositeKey.class)
public class OrderDetail extends BaseEntity {
    private int quantity;

    @Column(name = "product_id")
    @Id
    private String productId;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    @Id
    private Order order;

    @Column(name = "variant_id")
    @Id
    private String variantId;

}
