package br.com.meli.technicalchallenge.model.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    private String id;
    private String productId;
    private String url;
    private Integer order;

    public boolean isMain() {
        return order == 0;
    }
}