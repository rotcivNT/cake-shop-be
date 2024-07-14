package com.rotciv.order.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private String id;
    private String productId;
    private String sessionId;
    private int quantity;
    private String variantId;
}
