package com.rotciv.products.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter@Setter@ToString
public class BaseEntity {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
