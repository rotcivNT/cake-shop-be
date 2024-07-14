package com.rotciv.products.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "galleries")
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Gallery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String url;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @Override
    public String toString() {
        return "Gallery{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", url='" + url + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
