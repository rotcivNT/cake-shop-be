package com.rotciv.order.dto;

import lombok.Data;

@Data
public class PostPaymentDto {
    private String resVnPayCode;
    private String paymentId;
    private String orderId;
    private String userId;
}
