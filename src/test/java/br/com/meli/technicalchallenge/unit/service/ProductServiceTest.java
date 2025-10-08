
package br.com.meli.technicalchallenge.unit.service;

import br.com.meli.technicalchallenge.model.domain.actor.Store;
import br.com.meli.technicalchallenge.model.domain.actor.User;
import br.com.meli.technicalchallenge.model.domain.product.Brand;
import br.com.meli.technicalchallenge.model.domain.product.CharacteristicDefinition;
import br.com.meli.technicalchallenge.model.domain.product.Product;
import br.com.meli.technicalchallenge.model.domain.product.ProductCharacteristic;
import br.com.meli.technicalchallenge.model.domain.product.ProductImage;
import br.com.meli.technicalchallenge.model.domain.shipping.Shipping;
import br.com.meli.technicalchallenge.model.dto.response.product.ProductDetailDTO;
import br.com.meli.technicalchallenge.repository.product.BrandRepository;
import br.com.meli.technicalchallenge.repository.product.CharacteristicDefinitionRepository;
import br.com.meli.technicalchallenge.repository.product.ProductCharacteristicRepository;
import br.com.meli.technicalchallenge.repository.product.ProductImageRepository;
import br.com.meli.technicalchallenge.repository.product.ProductRepository;
import br.com.meli.technicalchallenge.service.AuthService;
import br.com.meli.technicalchallenge.service.ProductService;
import br.com.meli.technicalchallenge.service.ShippingService;
import br.com.meli.technicalchallenge.service.StoreService;
import br.com.meli.technicalchallenge.testutil.ProductTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Provides unit tests for the ProductService class.
 * Currently, the tests only guarantee happy-path functionality and are therefore incomplete.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    private ProductCharacteristicRepository productCharacteristicRepository;

    @Mock
    private CharacteristicDefinitionRepository characteristicDefinitionRepository;

    @Mock
    private StoreService storeService;

    @Mock
    private ShippingService shippingService;

    @Mock
    private AuthService authService;

    private ProductService productService;

    private Product testProduct;
    private Brand testBrand;
    private Store testStore;
    private User testUser;
    private List<ProductImage> testImages;
    private List<ProductCharacteristic> testCharacteristics;
    private CharacteristicDefinition testCharacteristicDefinition;
    private Shipping testShipping;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, brandRepository, productImageRepository,productCharacteristicRepository,
                characteristicDefinitionRepository, storeService, shippingService,authService);

        setupTestData();
    }

    /**
     * tests are incomplete due to time constraints, there should also be tests for:
     *  - product not found
     *  - corrupted entities
     *  - null pointer verifications
     *  - nullable fields such as images, shipping, etc.
     */
    @Test
    void getProductDetail_shouldReturnCompleteProductDetailWhenUserIsLoggedIn() {
        String productId = testProduct.getId();

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(brandRepository.findById(testProduct.getBrandId())).thenReturn(Optional.of(testBrand));
        when(storeService.getStore(testProduct.getStoreId())).thenReturn(Optional.of(testStore));
        when(productImageRepository.findByProductId(productId)).thenReturn(testImages);
        when(productCharacteristicRepository.findByProductId(productId)).thenReturn(testCharacteristics);
        when(characteristicDefinitionRepository.findById(testCharacteristicDefinition.getId())).thenReturn(Optional.of(testCharacteristicDefinition));
        when(authService.getLoggedUser()).thenReturn(Optional.of(testUser));
        when(shippingService.calculateShipping(testStore, testUser)).thenReturn(Optional.of(testShipping));

        ProductDetailDTO expected = ProductDetailDTO.from(testProduct, testBrand, testImages,
                productService.getProductCharacteristicsPairs(productId), testStore, Optional.of(testShipping));
        ProductDetailDTO actual = productService.getProductDetail(productId);

        assertThat(actual).isEqualTo(expected);
    }

    /**
     * tests are incomplete due to time constraints, there should also be tests for:
     *  - empty characteristics
     *  - unrelated pairs
     *  - 1:1 association between characteristics and definitions per product
     */
    @Test
    void getProductCharacteristicsPairs_shouldReturnCharacteristicPairsWhenCharacteristicsExist() {
        when(productCharacteristicRepository.findByProductId(testProduct.getId())).thenReturn(testCharacteristics);
        when(characteristicDefinitionRepository.findById(testCharacteristicDefinition.getId())).thenReturn(Optional.of(testCharacteristicDefinition));

        Set<Map.Entry<CharacteristicDefinition, ProductCharacteristic>> result =
                productService.getProductCharacteristicsPairs(testProduct.getId());

        assertThat(result).hasSize(1);
        Map.Entry<CharacteristicDefinition, ProductCharacteristic> entry = result.iterator().next();
        assertThat(entry.getKey()).isEqualTo(testCharacteristicDefinition);
        assertThat(entry.getValue()).isEqualTo(testCharacteristics.getFirst());
    }

    private void setupTestData() {
        testProduct = ProductTestDataBuilder.createTestProduct();
        testBrand = ProductTestDataBuilder.createTestBrand();
        testStore = ProductTestDataBuilder.createTestStore();
        testUser = ProductTestDataBuilder.createTestUser();
        testImages = ProductTestDataBuilder.createTestImages();
        testCharacteristics = ProductTestDataBuilder.createTestCharacteristics();
        testCharacteristicDefinition = ProductTestDataBuilder.createTestCharacteristicDefinition();
        testShipping = ProductTestDataBuilder.createTestShipping();

    }
}