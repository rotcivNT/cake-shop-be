package com.rotciv.order.services;

import com.rotciv.order.dto.ResponseSessionDto;
import com.rotciv.order.dto.ShoppingDto;
import com.rotciv.order.entity.ShoppingSession;
import org.apache.coyote.BadRequestException;

public interface ShoppingService {
    void handleShopping(ShoppingDto shoppingDto);
    ShoppingSession upsertShoppingSession(ShoppingSession sSession);
    ResponseSessionDto getShoppingSessionsByUserId(String userId) throws BadRequestException;
    void deleteShoppingSessionByUserId(String userId);
}
