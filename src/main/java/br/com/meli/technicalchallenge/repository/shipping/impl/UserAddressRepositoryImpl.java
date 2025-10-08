package br.com.meli.technicalchallenge.repository.shipping.impl;

import br.com.meli.technicalchallenge.model.domain.shipping.AddressEntityType;
import br.com.meli.technicalchallenge.repository.BaseJsonRepository;
import br.com.meli.technicalchallenge.model.domain.shipping.UserAddress;
import br.com.meli.technicalchallenge.repository.shipping.UserAddressRepository;
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
public class UserAddressRepositoryImpl extends BaseJsonRepository<UserAddress, String>
        implements UserAddressRepository {

    public UserAddressRepositoryImpl(ResourceLoader resourceLoader, ObjectMapper objectMapper,
                                     @Value("${application.data.path}") String dataPath) {
        super(resourceLoader, objectMapper, dataPath);
    }

    @Override
    protected String getFileName() {
        return "addresses.json";
    }

    @Override
    protected TypeReference<List<UserAddress>> getTypeReference() {
        return new TypeReference<List<UserAddress>>() {};
    }

    @Override
    protected Function<UserAddress, String> getKeyExtractor() {
        return UserAddress::getId;
    }

    @PostConstruct
    @Override
    public void loadData() throws IOException {
        Resource resource = resourceLoader.getResource(dataPath + getFileName());
        List<UserAddress> dataList = objectMapper.readValue(resource.getInputStream(), getTypeReference());
        this.dataMap = dataList.stream()
                .filter(address -> address.getEntityType() == AddressEntityType.USER)
                .collect(Collectors.toMap(getKeyExtractor(), Function.identity()));
    }

    @Override
    public Optional<UserAddress> findDefaultByUserId(String userId) {
        return dataMap.values().stream()
                .filter(address -> address.getEntityId().equals(userId) && address.isDefault())
                .findFirst();
    }
}