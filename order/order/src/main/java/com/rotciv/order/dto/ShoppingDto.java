package com.rotciv.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingDto {
    private ShoppingSessionDto shoppingSession;
    private List<CartItemDto> cartItems;
    private String type;
}
