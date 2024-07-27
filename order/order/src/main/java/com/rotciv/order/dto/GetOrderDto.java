package com.rotciv.order.dto;

import com.rotciv.order.entity.PaymentDetail;
import com.rotciv.order.enums.OrderEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetOrderDto {
    private String id;
    private String description;
    private String userJson;
    private long total;
    private PaymentDetail paymentDetail;
    private String shippingAddress;
    private List<ResponseOrderDetailDto> orderDetailsFull;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderEnum.OrderStatus status;
}
