package com.rotciv.order.dto;

import com.rotciv.order.enums.OrderEnum;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
public class ResponseOrderDetailDto {
    private String orderId;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String productJson;
    private String variantId;
    private String productId;
    private Double price;
}
