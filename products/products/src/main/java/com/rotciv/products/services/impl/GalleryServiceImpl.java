package com.rotciv.products.services.impl;

import com.rotciv.products.entities.Gallery;
import com.rotciv.products.entities.Product;
import com.rotciv.products.mapper.GalleryMapper;
import com.rotciv.products.repository.GalleryRepository;
import com.rotciv.products.services.GalleryService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class GalleryServiceImpl implements GalleryService {
    private GalleryRepository galleryRepository;
    @Override
    public void saveGallery(String productId, String[] url) {
        try {
            for (String s : url) {
                Gallery gallery = new Gallery();
                Product p = new Product();
                p.setId(productId);
                gallery.setProduct(p);
                gallery.setUrl(s);
                gallery.setCreatedAt(LocalDateTime.now());
                gallery.setUpdatedAt(LocalDateTime.now());
                galleryRepository.save(gallery);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save gallery");
        }
    }

    @Override
    public String getGallery(String productId) {
        return "";
    }
}
