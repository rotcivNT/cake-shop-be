package com.rotciv.products.repository;

import com.rotciv.products.entities.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends JpaRepository<Variant, String>{
    Variant findByVariantValueAndVariantKey(String variantValue, String variantKey);
}
