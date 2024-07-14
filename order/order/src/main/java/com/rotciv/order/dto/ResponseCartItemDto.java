package com.rotciv.order.dto;

import lombok.Data;

@Data
public class ResponseCartItemDto {
    private String id;
    private String productId;
    private String sessionId;
    private int quantity;
    private String productJson;
    private String variantId;
}
