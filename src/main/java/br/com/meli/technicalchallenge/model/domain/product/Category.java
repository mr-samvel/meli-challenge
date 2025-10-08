package br.com.meli.technicalchallenge.model.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private String id;
    private String name;
    private String parentCategoryId;

    public boolean isRoot() {
        return parentCategoryId == null;
    }
}