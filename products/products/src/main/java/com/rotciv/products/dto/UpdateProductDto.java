package com.rotciv.products.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateProductDto {
    @NotEmpty()
    String id;
    @NotEmpty()
    String name;
    @NotEmpty()
    String categoryId;

    String desc;

    @NotEmpty()
    private List<ProductVariantDto> productVariants;
}
