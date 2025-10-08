package br.com.meli.technicalchallenge.model.dto.response.product;

import br.com.meli.technicalchallenge.model.domain.product.CharacteristicDefinition;
import br.com.meli.technicalchallenge.model.domain.product.ProductCharacteristic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Product characteristic")
public class ProductCharacteristicDTO {
    @Schema(description = "Characteristic name", example = "Screen Size")
    private String name;

    @Schema(description = "Characteristic value", example = "6.5 inches")
    private String value;

    public static ProductCharacteristicDTO from(CharacteristicDefinition definition, ProductCharacteristic characteristic) {
        return ProductCharacteristicDTO.builder()
                .name(definition.getName())
                .value(characteristic.getValue())
                .build();
    }

    public static List<ProductCharacteristicDTO> from(Set<Map.Entry<CharacteristicDefinition, ProductCharacteristic>> characteristicsTuples) {
        return characteristicsTuples.stream()
                .map(tuple -> ProductCharacteristicDTO.from(tuple.getKey(), tuple.getValue()))
                .collect(Collectors.toList());
    }
}