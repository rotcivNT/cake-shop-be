package com.rotciv.products.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
public class CreateProductDto {
    private String id;
    @NotEmpty()
    private String name;

    private String desc;

    @NotEmpty()
    private String images;

    private String[] gallery;

    @NotEmpty()
    private String categoryId;

    private List<ProductVariantDto> productVariants;
}
