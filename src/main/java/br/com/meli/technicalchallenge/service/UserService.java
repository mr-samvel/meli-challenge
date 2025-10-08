package br.com.meli.technicalchallenge.service;

import br.com.meli.technicalchallenge.model.domain.shipping.UserAddress;
import br.com.meli.technicalchallenge.repository.shipping.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAddressRepository userAddressRepository;

    public Optional<UserAddress> getDefaultUserAddress(String userId) {
        return userAddressRepository.findDefaultByUserId(userId);
    }
}
