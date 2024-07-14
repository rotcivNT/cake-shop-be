package com.rotciv.products.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDto {
    String id;
    String name;
    String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    CategoryDto category;
    List<ProductVariantODto> variants;
}
