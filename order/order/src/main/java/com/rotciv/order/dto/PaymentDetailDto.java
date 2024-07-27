package com.rotciv.order.dto;

import com.rotciv.order.enums.OrderEnum;
import lombok.Data;

@Data
public class PaymentDetailDto {
    private String id;
    private long amount;
    private OrderEnum.PaymentStatus status;
    private String type;
}
