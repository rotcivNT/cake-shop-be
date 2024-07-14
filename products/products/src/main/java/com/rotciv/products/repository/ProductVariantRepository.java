package com.rotciv.products.repository;

import com.rotciv.products.entities.ProductVariant;
import com.rotciv.products.entities.ProductVariantCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, ProductVariantCompositeKey> {
    ProductVariant findByProductIdAndVariantId(String productId, String variantId);
}
