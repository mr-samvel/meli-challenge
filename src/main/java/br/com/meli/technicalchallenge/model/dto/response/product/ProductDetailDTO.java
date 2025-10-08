package br.com.meli.technicalchallenge.model.dto.response.product;

import br.com.meli.technicalchallenge.model.domain.actor.Store;
import br.com.meli.technicalchallenge.model.domain.product.Brand;
import br.com.meli.technicalchallenge.model.domain.product.CharacteristicDefinition;
import br.com.meli.technicalchallenge.model.domain.product.Product;
import br.com.meli.technicalchallenge.model.domain.product.ProductCharacteristic;
import br.com.meli.technicalchallenge.model.domain.product.ProductImage;
import br.com.meli.technicalchallenge.model.domain.shipping.Shipping;
import br.com.meli.technicalchallenge.model.dto.response.actor.StoreInfoDTO;
import br.com.meli.technicalchallenge.model.dto.response.shipping.ShippingInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Complete product detail information")
public class ProductDetailDTO {
    @Schema(description = "Product ID", example = "0199bb55-661f-7f45-af8c-d1b013911a16")
    private String id;

    @Schema(description = "Product title", example = "Samsung Galaxy S23 Ultra 256GB")
    private String title;

    @Schema(description = "Product description", example = "Premium smartphone with advanced camera system")
    private String description;

    @Schema(description = "Product price", example = "2300.00")
    private Float price;

    @Schema(description = "Available stock", example = "15")
    private Integer stock;

    @Schema(description = "Product brand")
    private BrandDTO brand;

    @Schema(description = "Product images")
    private List<ProductImageDTO> images;

    @Schema(description = "Product characteristics/specifications")
    private List<ProductCharacteristicDTO> characteristics;

    @Schema(description = "Store selling this product")
    private StoreInfoDTO store;

    @Schema(description = "Shipping information for logged user")
    private ShippingInfoDTO shipping;

    public static ProductDetailDTO from(Product product, Brand brand, List<ProductImage> images,
                                        Set<Map.Entry<CharacteristicDefinition, ProductCharacteristic>> characteristics,
                                        Store store, Optional<Shipping> shippingOpt) {
        return ProductDetailDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .brand(BrandDTO.from(brand))
                .images(ProductImageDTO.from(images))
                .characteristics(ProductCharacteristicDTO.from(characteristics))
                .store(StoreInfoDTO.from(store))
                .shipping(shippingOpt.map(ShippingInfoDTO::from).orElse(null))
                .build();
    }
}