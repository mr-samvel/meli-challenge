package br.com.meli.technicalchallenge.model.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacteristicDefinition {
    private String id;
    private String name;
    private String categoryId;
}