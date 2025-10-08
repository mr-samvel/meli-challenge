package br.com.meli.technicalchallenge.testutil;

import br.com.meli.technicalchallenge.model.domain.actor.Store;
import br.com.meli.technicalchallenge.model.domain.actor.User;
import br.com.meli.technicalchallenge.model.domain.product.Brand;
import br.com.meli.technicalchallenge.model.domain.product.CharacteristicDefinition;
import br.com.meli.technicalchallenge.model.domain.product.Product;
import br.com.meli.technicalchallenge.model.domain.product.ProductCharacteristic;
import br.com.meli.technicalchallenge.model.domain.product.ProductImage;
import br.com.meli.technicalchallenge.model.domain.shipping.Shipping;
import br.com.meli.technicalchallenge.model.domain.shipping.StoreAddress;
import br.com.meli.technicalchallenge.model.domain.shipping.UserAddress;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Test data builder utility class for creating test domain objects.
 * Provides convenient factory methods for creating test data in unit tests.
 */
public final class ProductTestDataBuilder {
    public static final String PRODUCT_ID = "PROD-001";
    public static final String BRAND_ID = "BRAND-001";
    public static final String STORE_ID = "STORE-001";
    public static final String CATEGORY_ID = "CAT-001";
    public static final String CHAR_DEF_ID = "CHAR_DEF-001";
    public static final String USER_ID = "USER-001";
    public static final String[] IMG_IDS = { "IMG-001", "IMG-002" };

    public static final StoreAddress STORE_ADDRESS = new StoreAddress();
    public static final UserAddress USER_ADDRESS = new UserAddress();

    private ProductTestDataBuilder() {}

    public static Product createTestProduct() {
        return new Product(PRODUCT_ID, "Test Product", "Test Description", 99.99f, 100,
                STORE_ID, BRAND_ID, CATEGORY_ID);
    }

    public static Brand createTestBrand() {
        return new Brand(BRAND_ID, "Test Brand");
    }

    public static Store createTestStore() {
        return new Store(STORE_ID, "Test Store", 5f, 1000, LocalDateTime.now());
    }

    public static User createTestUser() {
        return new User(USER_ID, "Test", "User", "test@user.com", LocalDateTime.now());
    }

    public static List<ProductImage> createTestImages() {
        AtomicInteger idx = new AtomicInteger(0);
        return Arrays.stream(IMG_IDS)
                .map(id -> new ProductImage(id, PRODUCT_ID, "https://example.com/"+id, idx.getAndIncrement()))
                .collect(Collectors.toList());
    }

    public static CharacteristicDefinition createTestCharacteristicDefinition() {
        return new CharacteristicDefinition(CHAR_DEF_ID, "Color", CATEGORY_ID);
    }

    public static List<ProductCharacteristic> createTestCharacteristics() {
        return List.of(new ProductCharacteristic(PRODUCT_ID, CHAR_DEF_ID, "Blue"));
    }

    public static Shipping createTestShipping() {
        return new Shipping(STORE_ADDRESS, USER_ADDRESS);
    }

    public static StoreAddress createStoreAddress() {
        return STORE_ADDRESS;
    }

    public static UserAddress createDefaultUserAddress() {
        return USER_ADDRESS;
    }
}