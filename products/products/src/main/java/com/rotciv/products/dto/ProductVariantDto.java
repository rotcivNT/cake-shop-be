package com.rotciv.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDto {
    private String productId;
    private String variantId;
    private String variantKey;
    private String variantValue;
    private long price;
}
