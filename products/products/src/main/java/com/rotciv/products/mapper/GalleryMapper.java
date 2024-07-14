package com.rotciv.products.mapper;

import com.rotciv.products.dto.CreateGalleryDto;
import com.rotciv.products.entities.Gallery;
import com.rotciv.products.entities.Product;

public class GalleryMapper {
    public static Gallery createDtoMapToEntity(CreateGalleryDto createGalleryDto, Gallery gallery) {
        Product product = new Product();
        product.setId(createGalleryDto.getProductId());
        gallery.setProduct(product);
        gallery.setUrl(createGalleryDto.getUrl());
        return gallery;
    }

    public static CreateGalleryDto mapToCreateDto(Gallery gallery) {
        CreateGalleryDto createGalleryDto = new CreateGalleryDto();
        createGalleryDto.setUrl(gallery.getUrl());
        return createGalleryDto;
    }
}
