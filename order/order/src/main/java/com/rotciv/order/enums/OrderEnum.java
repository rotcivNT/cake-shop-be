package com.rotciv.order.enums;

public class OrderEnum {
    public enum OrderStatus {
        PENDING,
        PROCESSING,
        SHIPPED,
        SHIPPING,
        CANCELLED
    }
    public enum PaymentStatus {
        PENDING,
        PAID,
        FAILED
    }
}
