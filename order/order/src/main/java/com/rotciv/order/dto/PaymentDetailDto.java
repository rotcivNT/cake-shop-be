package com.rotciv.order.dto;

import lombok.Data;

@Data
public class PaymentDetailDto {
    private String id;
    private long amount;
    private String status;
    private String type;
}
