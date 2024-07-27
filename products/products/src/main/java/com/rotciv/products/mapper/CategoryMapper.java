package com.rotciv.products.mapper;

import com.rotciv.products.dto.CreateCategoryDto;
import com.rotciv.products.entities.Category;

public class CategoryMapper {
    public static Category createDtoMapToEntity(CreateCategoryDto createCategoryDto, Category category) {
        if (createCategoryDto.getParentId() == null) {
            category.setParent(null);
        }
        else {
            Category parent = new Category();
            parent.setId(createCategoryDto.getParentId());
            category.setParent(parent);
        }
        category.setName(createCategoryDto.getName());
        return category;
    }

    public static CreateCategoryDto mapToCreateDto(Category category) {
        CreateCategoryDto createCategoryDto = new CreateCategoryDto();
        createCategoryDto.setName(category.getName());
        return createCategoryDto;
    }
}
