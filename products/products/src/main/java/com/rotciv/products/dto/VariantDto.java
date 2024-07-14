package com.rotciv.products.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VariantDto {
    private String id;
    private String variantKey;
    private String variantValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
