package br.com.meli.technicalchallenge.model.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String title;
    private String description;
    private Float price;
    private Integer stock;
    private String storeId;
    private String brandId;
    private String categoryId;

    public boolean isAvailable() {
        return stock > 0;
    }

    public boolean canBePurchased(int quantity) {
        return isAvailable() && stock >= quantity;
    }
}