package com.rotciv.products.dto;

import com.rotciv.products.entities.Product;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseProductDto {
    Product product;
    String categoryId;
    String categoryName;
    String parentCategoryId;
    String parentCategoryName;
}
