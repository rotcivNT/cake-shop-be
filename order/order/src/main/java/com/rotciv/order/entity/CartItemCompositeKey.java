package com.rotciv.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemCompositeKey {
    private String productId;

    private ShoppingSession shoppingSession;

    private String variantId;
}
