package br.com.meli.technicalchallenge.service;

import br.com.meli.technicalchallenge.model.domain.actor.Store;
import br.com.meli.technicalchallenge.model.domain.actor.User;
import br.com.meli.technicalchallenge.model.domain.shipping.Shipping;
import br.com.meli.technicalchallenge.model.domain.shipping.StoreAddress;
import br.com.meli.technicalchallenge.model.domain.shipping.UserAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final StoreService storeService;
    private final UserService userService;

    /**
     * Calculates shipping information between a store and a user.
     * <p>This method retrieves the store's address and the user's default shipping address,
     * then creates a Shipping object containing shipping details (cost, estimated delivery days, etc.).
     *
     * <p>If the user has no default address configured, shipping cannot be calculated
     * and an empty Optional is returned.
     *
     * @param store the store with the origin address
     * @param user  the user with the default destination address
     * @return Optional containing Shipping information if user has a default address, empty otherwise
     */
    public Optional<Shipping> calculateShipping(Store store, User user) {
        StoreAddress storeAddress = storeService.getStoreAddress(store.getId());
        Optional<UserAddress> userAddressOpt = userService.getDefaultUserAddress(user.getId());

        return userAddressOpt.map(userAddress -> new Shipping(storeAddress, userAddress));
    }
}