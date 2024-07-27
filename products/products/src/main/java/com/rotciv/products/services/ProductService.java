package com.rotciv.products.services;

import com.rotciv.products.dto.CreateProductDto;
import com.rotciv.products.dto.ProductVariantDto;
import com.rotciv.products.dto.ResponseProductDto;
import com.rotciv.products.dto.UpdateProductDto;
import com.rotciv.products.entities.Product;
import com.rotciv.products.entities.Variant;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductDto createProductDto);
    List<ResponseProductDto> getAllProducts(int page, int size, String query, String categoryId);
    void createVariant(ProductVariantDto productVariantDto);
    List<Variant> getAllVariants();
    boolean updateProduct(UpdateProductDto updateProductDto);
    Product getProductById(String id);
    boolean updateStatus(String id, Boolean isStop);
}
