package br.com.meli.technicalchallenge.repository.product.impl;

import br.com.meli.technicalchallenge.repository.BaseJsonRepository;
import br.com.meli.technicalchallenge.model.domain.product.Product;
import br.com.meli.technicalchallenge.repository.product.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class ProductRepositoryImpl extends BaseJsonRepository<Product, String> implements ProductRepository {

    public ProductRepositoryImpl(ResourceLoader resourceLoader, ObjectMapper objectMapper,
                                 @Value("${application.data.path}") String dataPath) {
        super(resourceLoader, objectMapper, dataPath);
    }

    @Override
    protected String getFileName() {
        return "products.json";
    }

    @Override
    protected TypeReference<List<Product>> getTypeReference() {
        return new TypeReference<List<Product>>() {};
    }

    @Override
    protected Function<Product, String> getKeyExtractor() {
        return Product::getId;
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(dataMap.get(id));
    }
}