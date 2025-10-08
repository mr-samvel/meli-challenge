package br.com.meli.technicalchallenge.repository.product.impl;

import br.com.meli.technicalchallenge.repository.BaseJsonRepository;
import br.com.meli.technicalchallenge.model.domain.product.ProductCharacteristic;
import br.com.meli.technicalchallenge.repository.product.ProductCharacteristicRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class ProductCharacteristicRepositoryImpl extends BaseJsonRepository<ProductCharacteristic, String>
        implements ProductCharacteristicRepository {

    public ProductCharacteristicRepositoryImpl(ResourceLoader resourceLoader, ObjectMapper objectMapper,
                                               @Value("${application.data.path}") String dataPath) {
        super(resourceLoader, objectMapper, dataPath);
    }

    @Override
    protected String getFileName() {
        return "product_characteristics.json";
    }

    @Override
    protected TypeReference<List<ProductCharacteristic>> getTypeReference() {
        return new TypeReference<List<ProductCharacteristic>>() {};
    }

    @Override
    protected Function<ProductCharacteristic, String> getKeyExtractor() {
        // ProductCharacteristic doesn't have an ID, so we generate one
        return characteristic -> UUID.randomUUID().toString();
    }

    @Override
    public List<ProductCharacteristic> findByProductId(String productId) {
        return dataMap.values().stream()
                .filter(characteristic -> characteristic.getProductId().equals(productId))
                .collect(Collectors.toList());
    }
}