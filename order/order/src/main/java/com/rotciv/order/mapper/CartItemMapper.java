package com.rotciv.order.mapper;

import com.rotciv.order.dto.CartItemDto;
import com.rotciv.order.entity.CartItem;
import com.rotciv.order.entity.ShoppingSession;

public class CartItemMapper {
    public static CartItem mapToCartItem(CartItemDto cartItemDto, CartItem cartItem) {
        ShoppingSession session = new ShoppingSession();
        session.setId(cartItemDto.getSessionId());
        cartItem.setProductId(cartItemDto.getProductId());
        cartItem.setShoppingSession(session);
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setVariantId(cartItemDto.getVariantId());
        return cartItem;
    }
}
