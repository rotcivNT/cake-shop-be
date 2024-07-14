package com.rotciv.order.dto;

import lombok.Data;

@Data
public class OrderDetailDto {
    private String id;
    private String productId;
    private int quantity;
    private String orderId;
    private String variantId;
}
