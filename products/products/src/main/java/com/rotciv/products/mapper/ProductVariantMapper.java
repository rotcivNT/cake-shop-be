package com.rotciv.products.mapper;

import com.rotciv.products.dto.ProductVariantDto;
import com.rotciv.products.entities.Product;
import com.rotciv.products.entities.ProductVariant;
import com.rotciv.products.entities.Variant;

public class ProductVariantMapper {
    public static ProductVariant toEntity(ProductVariantDto productVariantDto, ProductVariant productVariant, Product product, Variant variant) {

        productVariant.setVariant(variant);
        productVariant.setProduct(product);
        productVariant.setPrice(productVariantDto.getPrice());
        return productVariant;
    }
}
