package com.rotciv.products.mapper;

import com.rotciv.products.dto.CreateProductDto;
import com.rotciv.products.dto.ResponseProductDto;
import com.rotciv.products.dto.UpdateProductDto;
import com.rotciv.products.entities.Category;
import com.rotciv.products.entities.Gallery;
import com.rotciv.products.entities.Product;
import org.hibernate.sql.Update;

public class ProductMapper {
    public static Product createDtoMapToEntity(CreateProductDto createProductDto, Product product) {
        Category category = new Category();
        category.setId(createProductDto.getCategoryId());

        product.setName(createProductDto.getName());
        product.setDescription(createProductDto.getDesc());
        product.setThumbnail(createProductDto.getImages());
        product.setCategory(category);
        return product;
    }

    public static CreateProductDto mapToCreateDto(Product product) {
        CreateProductDto createProductDto = new CreateProductDto();
        createProductDto.setName(product.getName());
        createProductDto.setDesc(product.getDescription());
        createProductDto.setImages(product.getThumbnail());
        return createProductDto;
    }

    public static ResponseProductDto mapToDto(Product product) {
        ResponseProductDto responseProductDto = new ResponseProductDto();
        responseProductDto.setProduct(product);
        responseProductDto.setCategoryName(product.getCategory().getName());
        responseProductDto.setCategoryId(product.getCategory().getId());
        responseProductDto.setParentCategoryId(product.getCategory().getParent().getId());
        responseProductDto.setParentCategoryName(product.getCategory().getParent().getName());
        return responseProductDto;
    }
}
