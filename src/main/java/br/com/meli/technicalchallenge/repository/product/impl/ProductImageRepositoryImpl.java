package br.com.meli.technicalchallenge.repository.product.impl;

import br.com.meli.technicalchallenge.repository.BaseJsonRepository;
import br.com.meli.technicalchallenge.model.domain.product.ProductImage;
import br.com.meli.technicalchallenge.repository.product.ProductImageRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class ProductImageRepositoryImpl extends BaseJsonRepository<ProductImage, String>
        implements ProductImageRepository {

    public ProductImageRepositoryImpl(ResourceLoader resourceLoader, ObjectMapper objectMapper,
                                      @Value("${application.data.path}") String dataPath) {
        super(resourceLoader, objectMapper, dataPath);
    }

    @Override
    protected String getFileName() {
        return "product_images.json";
    }

    @Override
    protected TypeReference<List<ProductImage>> getTypeReference() {
        return new TypeReference<List<ProductImage>>() {};
    }

    @Override
    protected Function<ProductImage, String> getKeyExtractor() {
        return ProductImage::getId;
    }

    @Override
    public List<ProductImage> findByProductId(String productId) {
        return dataMap.values().stream()
                .filter(image -> image.getProductId().equals(productId))
                .sorted(Comparator.comparing(ProductImage::getOrder))
                .collect(Collectors.toList());
    }
}