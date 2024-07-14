package com.rotciv.order.dto;

import com.rotciv.order.entity.ShoppingSession;
import lombok.Data;

import java.util.List;

@Data
public class ResponseSessionDto {
    private ShoppingSession shoppingSession;
    private List<ResponseCartItemDto> cartItems;
}
