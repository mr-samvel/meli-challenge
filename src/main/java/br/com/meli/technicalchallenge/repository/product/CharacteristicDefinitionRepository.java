package br.com.meli.technicalchallenge.repository.product;

import br.com.meli.technicalchallenge.model.domain.product.CharacteristicDefinition;

import java.util.Optional;

public interface CharacteristicDefinitionRepository {
    Optional<CharacteristicDefinition> findById(String id);
}