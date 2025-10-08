package br.com.meli.technicalchallenge.repository.shipping;

import br.com.meli.technicalchallenge.model.domain.shipping.StoreAddress;

import java.util.Optional;

public interface StoreAddressRepository {
    Optional<StoreAddress> findByStoreId(String storeId);
}