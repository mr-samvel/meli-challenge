package br.com.meli.technicalchallenge.repository.product;

import br.com.meli.technicalchallenge.model.domain.product.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(String id);
}