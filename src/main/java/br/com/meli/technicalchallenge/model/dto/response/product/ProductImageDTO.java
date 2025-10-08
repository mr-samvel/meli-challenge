package br.com.meli.technicalchallenge.model.dto.response.product;

import br.com.meli.technicalchallenge.model.domain.product.ProductImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Product image")
public class ProductImageDTO {
    @Schema(description = "Image URL", example = "https://example.com/image.jpg")
    private String url;

    @Schema(description = "Image show order (0 = main image)", example = "0")
    private Integer order;

    public static ProductImageDTO from(ProductImage image) {
        return ProductImageDTO.builder()
                .url(image.getUrl())
                .order(image.getOrder())
                .build();
    }
    
    public static List<ProductImageDTO> from(List<ProductImage> images) {
        return images.stream()
                .map(ProductImageDTO::from)
                .collect(Collectors.toList());
    }
}