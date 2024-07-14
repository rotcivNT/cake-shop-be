package com.rotciv.products.services;


import com.rotciv.products.dto.CreateCategoryDto;
import com.rotciv.products.entities.Category;

import java.util.List;

public interface CategoryService {
    public void createCategory(CreateCategoryDto createCategoryDto);
    public List<Category> getAllCategories();
    public Category getCategoryById(String id);
}
