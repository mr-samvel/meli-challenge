package br.com.meli.technicalchallenge.model.dto.response.product;

import br.com.meli.technicalchallenge.model.domain.product.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Product brand information")
public class BrandDTO {
    @Schema(description = "Brand id", example = "0199bb51-3d73-7ea5-b8f0-b38436e41eba")
    private String id;
    @Schema(description = "Brand name", example = "Samsung")
    private String name;

    public static BrandDTO from(Brand brand) {
        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}