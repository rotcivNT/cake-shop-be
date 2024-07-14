package com.rotciv.products.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "variants")
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@ToString
public class Variant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String variantKey;

    private String variantValue;

    @OneToMany(mappedBy = "variant")
    @JsonBackReference
    private List<ProductVariant> productVariants;

}
