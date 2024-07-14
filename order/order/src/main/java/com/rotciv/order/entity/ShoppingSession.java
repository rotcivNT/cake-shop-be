package com.rotciv.order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "shopping_sessions")
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class ShoppingSession extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String description;

    @Column(name = "user_id", unique = true)
    private String userId;

    @OneToMany(mappedBy = "shoppingSession")
    @JsonManagedReference
    private List<CartItem> cartItems;
}
