package br.com.meli.technicalchallenge.repository.product;

import br.com.meli.technicalchallenge.model.domain.product.Brand;

import java.util.Optional;

public interface BrandRepository {
    Optional<Brand> findById(String id);
}