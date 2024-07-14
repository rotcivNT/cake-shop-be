package com.rotciv.order.entity;

import lombok.Data;

@Data
public class OrderDetailCompositeKey {
    private String productId;

    private Order order;

    private String variantId;
}
