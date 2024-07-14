package com.rotciv.order.services;

import com.rotciv.order.dto.ResponseSessionDto;
import com.rotciv.order.dto.ShoppingDto;
import com.rotciv.order.entity.ShoppingSession;

public interface ShoppingService {
    void handleShopping(ShoppingDto shoppingDto);
    ShoppingSession upsertShoppingSession(ShoppingSession sSession);
    ResponseSessionDto getShoppingSessionsByUserId(String userId);
    void deleteShoppingSessionByUserId(String userId);
}
