package br.com.meli.technicalchallenge.model.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCharacteristic {
    private String productId;
    private String characteristicDefinitionId;
    private String value;
}