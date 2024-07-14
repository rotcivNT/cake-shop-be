package com.rotciv.products.services.impl;

import com.rotciv.products.dto.CreateCategoryDto;
import com.rotciv.products.entities.Category;
import com.rotciv.products.entities.Product;
import com.rotciv.products.entities.ProductVariant;
import com.rotciv.products.mapper.CategoryMapper;
import com.rotciv.products.repository.CategoryRepository;
import com.rotciv.products.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    @Override
    public void createCategory(CreateCategoryDto createCategoryDto) {
        Category createdCategory = CategoryMapper.createDtoMapToEntity(createCategoryDto, new Category());
        Category category = this.categoryRepository.findByName(createCategoryDto.getName());
        if (category != null) {
            throw new RuntimeException("Category already exist");
        }
        createdCategory.setCreatedAt(LocalDateTime.now());
        createdCategory.setUpdatedAt(LocalDateTime.now());
        this.categoryRepository.save(createdCategory);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new RuntimeException("No categories found");
        }
        return categories;
    }

    @Override
    public Category getCategoryById(String id) {
        Sort sort = Sort.by(Sort.Direction.DESC, "products_productVariants_price");
        Category category = this.categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        for (Product p: category.getProducts()) {
            p.getProductVariants().sort((ProductVariant pv1, ProductVariant pv2) -> pv2.getPrice() - pv1.getPrice() > 0 ? -1: 1);
        }
        return category;
    }
}
