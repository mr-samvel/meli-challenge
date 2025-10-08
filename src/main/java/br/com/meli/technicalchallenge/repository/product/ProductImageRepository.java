package br.com.meli.technicalchallenge.repository.product;

import br.com.meli.technicalchallenge.model.domain.product.ProductImage;

import java.util.List;

public interface ProductImageRepository {
    List<ProductImage> findByProductId(String productId);
}