package br.com.meli.technicalchallenge.repository.actor.impl;

import br.com.meli.technicalchallenge.repository.BaseJsonRepository;
import br.com.meli.technicalchallenge.model.domain.actor.Store;
import br.com.meli.technicalchallenge.repository.actor.StoreRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class StoreRepositoryImpl extends BaseJsonRepository<Store, String> implements StoreRepository {

    public StoreRepositoryImpl(ResourceLoader resourceLoader, ObjectMapper objectMapper,
                               @Value("${application.data.path}") String dataPath) {
        super(resourceLoader, objectMapper, dataPath);
    }

    @Override
    protected String getFileName() {
        return "stores.json";
    }

    @Override
    protected TypeReference<List<Store>> getTypeReference() {
        return new TypeReference<List<Store>>() {};
    }

    @Override
    protected Function<Store, String> getKeyExtractor() {
        return Store::getId;
    }

    @Override
    public Optional<Store> findById(String id) {
        return Optional.ofNullable(dataMap.get(id));
    }
}