package br.com.meli.technicalchallenge.repository.shipping.impl;

import br.com.meli.technicalchallenge.model.domain.shipping.AddressEntityType;
import br.com.meli.technicalchallenge.repository.BaseJsonRepository;
import br.com.meli.technicalchallenge.model.domain.shipping.StoreAddress;
import br.com.meli.technicalchallenge.repository.shipping.StoreAddressRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class StoreAddressRepositoryImpl extends BaseJsonRepository<StoreAddress, String>
        implements StoreAddressRepository {

    public StoreAddressRepositoryImpl(ResourceLoader resourceLoader, ObjectMapper objectMapper,
                                      @Value("${application.data.path}") String dataPath) {
        super(resourceLoader, objectMapper, dataPath);
    }

    @Override
    protected String getFileName() {
        return "addresses.json";
    }

    @Override
    protected TypeReference<List<StoreAddress>> getTypeReference() {
        return new TypeReference<List<StoreAddress>>() {};
    }

    @Override
    protected Function<StoreAddress, String> getKeyExtractor() {
        return StoreAddress::getId;
    }

    @PostConstruct
    @Override
    public void loadData() throws IOException {
        Resource resource = resourceLoader.getResource(dataPath + getFileName());
        List<StoreAddress> dataList = objectMapper.readValue(resource.getInputStream(), getTypeReference());
        this.dataMap = dataList.stream()
                .filter(address -> address.getEntityType() == AddressEntityType.STORE)
                .collect(Collectors.toMap(getKeyExtractor(), Function.identity()));
    }

    @Override
    public Optional<StoreAddress> findByStoreId(String storeId) {
        return dataMap.values().stream()
                .filter(address -> address.getEntityId().equals(storeId) && address.isDefault())
                .findFirst();
    }
}