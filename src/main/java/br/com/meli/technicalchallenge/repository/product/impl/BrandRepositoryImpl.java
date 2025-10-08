package br.com.meli.technicalchallenge.repository.product.impl;

import br.com.meli.technicalchallenge.repository.BaseJsonRepository;
import br.com.meli.technicalchallenge.model.domain.product.Brand;
import br.com.meli.technicalchallenge.repository.product.BrandRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class BrandRepositoryImpl extends BaseJsonRepository<Brand, String> implements BrandRepository {

    public BrandRepositoryImpl(ResourceLoader resourceLoader, ObjectMapper objectMapper,
                               @Value("${application.data.path}") String dataPath) {
        super(resourceLoader, objectMapper, dataPath);
    }

    @Override
    protected String getFileName() {
        return "brands.json";
    }

    @Override
    protected TypeReference<List<Brand>> getTypeReference() {
        return new TypeReference<List<Brand>>() {};
    }

    @Override
    protected Function<Brand, String> getKeyExtractor() {
        return Brand::getId;
    }

    @Override
    public Optional<Brand> findById(String id) {
        return Optional.ofNullable(dataMap.get(id));
    }
}