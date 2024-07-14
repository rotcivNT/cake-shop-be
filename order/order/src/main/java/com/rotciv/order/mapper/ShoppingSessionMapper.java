package com.rotciv.order.mapper;

import com.rotciv.order.dto.ShoppingSessionDto;
import com.rotciv.order.entity.ShoppingSession;

public class ShoppingSessionMapper {
    public static ShoppingSession mapToShoppingSession(ShoppingSessionDto shoppingSessionDto, ShoppingSession shoppingSession) {
       shoppingSession.setUserId(shoppingSessionDto.getUserId());
       shoppingSession.setId(shoppingSessionDto.getId());
       shoppingSession.setDescription(shoppingSessionDto.getDescription());
       return shoppingSession;
    }
}
