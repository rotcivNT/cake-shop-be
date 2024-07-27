package com.rotciv.order.dto;

import com.rotciv.order.enums.OrderEnum;
import lombok.Data;

@Data
public class OrderDto {
    private String id;
    private String description;
    private String userId;
    private long total;
    private String payment_id;
    private String shippingAddress;
    private OrderEnum.OrderStatus status;
}
