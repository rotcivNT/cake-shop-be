package com.rotciv.products.dto;

import lombok.Data;

@Data
public class ProductVariantODto {
    private String id;
    private long price;
    private String productId;
    private VariantDto variant;
}
