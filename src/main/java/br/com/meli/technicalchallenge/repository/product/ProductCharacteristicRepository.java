package br.com.meli.technicalchallenge.repository.product;

import br.com.meli.technicalchallenge.model.domain.product.ProductCharacteristic;

import java.util.List;

public interface ProductCharacteristicRepository {
    List<ProductCharacteristic> findByProductId(String productId);
}