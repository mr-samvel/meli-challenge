package br.com.meli.technicalchallenge.repository.product.impl;

import br.com.meli.technicalchallenge.repository.BaseJsonRepository;
import br.com.meli.technicalchallenge.model.domain.product.CharacteristicDefinition;
import br.com.meli.technicalchallenge.repository.product.CharacteristicDefinitionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class CharacteristicDefinitionRepositoryImpl extends BaseJsonRepository<CharacteristicDefinition, String>
        implements CharacteristicDefinitionRepository {

    public CharacteristicDefinitionRepositoryImpl(ResourceLoader resourceLoader, ObjectMapper objectMapper,
                                                  @Value("${application.data.path}") String dataPath) {
        super(resourceLoader, objectMapper, dataPath);
    }

    @Override
    protected String getFileName() {
        return "characteristic_definitions.json";
    }

    @Override
    protected TypeReference<List<CharacteristicDefinition>> getTypeReference() {
        return new TypeReference<List<CharacteristicDefinition>>() {};
    }

    @Override
    protected Function<CharacteristicDefinition, String> getKeyExtractor() {
        return CharacteristicDefinition::getId;
    }

    @Override
    public Optional<CharacteristicDefinition> findById(String id) {
        return Optional.ofNullable(dataMap.get(id));
    }
}