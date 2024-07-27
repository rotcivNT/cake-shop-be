package com.rotciv.products.controllers;

import com.rotciv.products.dto.CreateCategoryDto;
import com.rotciv.products.dto.ResponseDto;
import com.rotciv.products.entities.Category;
import com.rotciv.products.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/api/category", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/get-all-category")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCategory(CreateCategoryDto createCategoryDto) {
        try {
            categoryService.createCategory(createCategoryDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body((new ResponseDto("201", "Category was created successfully")));
        } catch(Exception e) {
            throw new RuntimeException("Error creating category" + e.getMessage());
        }
    }

    @GetMapping("/get-category/{id}")
    public Category getCategoryById(@PathVariable("id") String id){
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/get-child-category/{id}")
    public List<Category> getChildCategory(@PathVariable("id") String id) {
        return categoryService.getChildCategory(id);
    }

    @GetMapping("/get-category-by-name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable("name") String name) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(categoryService.getCategoryByName(name));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto("404", "Category not found"));
        }
    }
}
