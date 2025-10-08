package br.com.meli.technicalchallenge.repository.shipping;

import br.com.meli.technicalchallenge.model.domain.shipping.UserAddress;

import java.util.Optional;

public interface UserAddressRepository {
    Optional<UserAddress> findDefaultByUserId(String userId);
}