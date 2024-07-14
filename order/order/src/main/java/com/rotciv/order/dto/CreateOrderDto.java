package com.rotciv.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {
    private OrderDto order;
    private PaymentDetailDto paymentDetail;
    private List<OrderDetailDto> orderDetails;
}
