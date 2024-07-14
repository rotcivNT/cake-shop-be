package com.rotciv.products.mapper;

import com.rotciv.products.dto.ProductVariantDto;
import com.rotciv.products.entities.Variant;

public class VariantMapper {
    public static Variant toEntity(ProductVariantDto productVariantDto, Variant variant) {
        variant.setVariantKey(productVariantDto.getVariantKey());
        variant.setVariantValue(productVariantDto.getVariantValue());

        return variant;
    }
}
