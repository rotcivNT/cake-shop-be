package com.rotciv.order.dto;

import com.rotciv.order.enums.OrderEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FilterOrderDto {
    private OrderEnum.OrderStatus status;
    private String startedAt;
    private String endedAt;
}
