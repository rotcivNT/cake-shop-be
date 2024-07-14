package com.rotciv.products.services;

public interface GalleryService {
    void saveGallery(String productId, String[] url);
    String getGallery(String productId);
}
