package com.rotciv.products.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateGalleryDto {
    @NotEmpty()
    private String productId;

    @NotEmpty()
    private String url;
}
