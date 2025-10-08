package br.com.meli.technicalchallenge.service;

import br.com.meli.technicalchallenge.exception.meliexception.CorruptedEntityException;
import br.com.meli.technicalchallenge.model.domain.actor.Store;
import br.com.meli.technicalchallenge.model.domain.shipping.StoreAddress;
import br.com.meli.technicalchallenge.repository.actor.StoreRepository;
import br.com.meli.technicalchallenge.repository.shipping.StoreAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreAddressRepository storeAddressRepository;

    public Optional<Store> getStore(String storeId) {
        return storeRepository.findById(storeId);
    }

    public StoreAddress getStoreAddress(String storeId) {
        return storeAddressRepository.findByStoreId(storeId)
                .orElseThrow(() -> new CorruptedEntityException("Store", storeId, "StoreAddress", storeId));
    }
}