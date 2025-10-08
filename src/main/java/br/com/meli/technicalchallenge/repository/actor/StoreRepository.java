package br.com.meli.technicalchallenge.repository.actor;

import br.com.meli.technicalchallenge.model.domain.actor.Store;

import java.util.Optional;

public interface StoreRepository {
    Optional<Store> findById(String id);
}