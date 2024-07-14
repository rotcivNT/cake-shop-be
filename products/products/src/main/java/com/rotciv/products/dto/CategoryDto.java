package com.rotciv.products.dto;

import com.rotciv.products.entities.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDto {
    String id;
    String name;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Category parent;
}
