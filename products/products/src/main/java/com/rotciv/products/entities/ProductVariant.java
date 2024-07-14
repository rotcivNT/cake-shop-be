package com.rotciv.products.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_variants")
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter
@IdClass(ProductVariantCompositeKey.class)
public class ProductVariant extends BaseEntity {
    private long price;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    @Id
    @JsonBackReference
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "variant_id")
    @Id
    private Variant variant;

    @Override
    public String toString() {
        return "ProductVariant{" +
                "price=" + price +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", variant=" + variant +
                '}';
    }
}
