package com.rotciv.products.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class CreateCategoryDto {
    @NotEmpty()
    private String name;

    private String parentId;
}
