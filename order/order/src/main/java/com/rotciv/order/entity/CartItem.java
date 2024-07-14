package com.rotciv.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
@IdClass(CartItemCompositeKey.class)
public class CartItem extends BaseEntity {

    private int quantity;

    @Column(name = "product_id")
    @Id
    private String productId;

    @Column(name = "variant_id")
    @Id
    private String variantId;

    @ManyToOne()
    @JoinColumn(name = "session_id")
    @JsonBackReference
    @Id
    private ShoppingSession shoppingSession;
}
