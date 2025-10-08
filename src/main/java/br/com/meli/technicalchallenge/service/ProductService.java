package br.com.meli.technicalchallenge.service;

import br.com.meli.technicalchallenge.exception.meliexception.CorruptedEntityException;
import br.com.meli.technicalchallenge.exception.meliexception.EntityNotFoundException;
import br.com.meli.technicalchallenge.model.domain.actor.Store;
import br.com.meli.technicalchallenge.model.domain.actor.User;
import br.com.meli.technicalchallenge.model.domain.product.Brand;
import br.com.meli.technicalchallenge.model.domain.product.CharacteristicDefinition;
import br.com.meli.technicalchallenge.model.domain.product.Product;
import br.com.meli.technicalchallenge.model.domain.product.ProductCharacteristic;
import br.com.meli.technicalchallenge.model.domain.product.ProductImage;
import br.com.meli.technicalchallenge.model.domain.shipping.Shipping;
import br.com.meli.technicalchallenge.model.dto.response.product.BrandDTO;
import br.com.meli.technicalchallenge.model.dto.response.product.ProductCharacteristicDTO;
import br.com.meli.technicalchallenge.model.dto.response.product.ProductDetailDTO;
import br.com.meli.technicalchallenge.model.dto.response.product.ProductImageDTO;
import br.com.meli.technicalchallenge.model.dto.response.shipping.ShippingInfoDTO;
import br.com.meli.technicalchallenge.model.dto.response.actor.StoreInfoDTO;
import br.com.meli.technicalchallenge.repository.product.BrandRepository;
import br.com.meli.technicalchallenge.repository.product.CharacteristicDefinitionRepository;
import br.com.meli.technicalchallenge.repository.product.ProductCharacteristicRepository;
import br.com.meli.technicalchallenge.repository.product.ProductImageRepository;
import br.com.meli.technicalchallenge.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductCharacteristicRepository productCharacteristicRepository;
    private final CharacteristicDefinitionRepository characteristicDefinitionRepository;

    private final StoreService storeService;
    private final ShippingService shippingService;
    private final AuthService authService;

    /**
     * Retrieves complete product details including brand, images, characteristics, store info, and shipping 
     * for the current logged-in user.
     *
     * <p>Note: In a real application with a relational database, this method would be more concise
     * as the associations would be handled by the ORM.
     *
     * @param productId the unique identifier of the product
     * @return ProductDetailDTO containing all product details
     * @throws EntityNotFoundException if the product with the given ID doesn't exist
     * @throws CorruptedEntityException if the product references non-existent related entities
     *                                   (brand, store, or characteristic definitions)
     */
    public ProductDetailDTO getProductDetail(String productId) throws EntityNotFoundException, CorruptedEntityException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(productId, "Product"));

        Brand brand = brandRepository.findById(product.getBrandId())
                .orElseThrow(() -> new CorruptedEntityException("Product", productId, "Brand", product.getBrandId()));

        Store store = storeService.getStore(product.getStoreId())
                .orElseThrow(() -> new CorruptedEntityException("Product", productId, "Store", product.getStoreId()));

        List<ProductImage> images = productImageRepository.findByProductId(productId);

        Set<Map.Entry<CharacteristicDefinition, ProductCharacteristic>> characteristicsTuples = getProductCharacteristicsPairs(productId);

        User loggedUser = authService.getLoggedUser().orElse(null);
        Optional<Shipping> shipping = loggedUser != null
                ? shippingService.calculateShipping(store, loggedUser)
                : Optional.empty();

        return ProductDetailDTO.from(product, brand, images, characteristicsTuples, store, shipping);
    }

    /**
     * Retrieves product characteristics individually paired with their definitions.
     *
     * @param productId the unique identifier of the product
     * @return Set of map entries where key is CharacteristicDefinition and value is ProductCharacteristic
     * @throws CorruptedEntityException if a product characteristic references a non-existent characteristic definition
     */
    public Set<Map.Entry<CharacteristicDefinition, ProductCharacteristic>> getProductCharacteristicsPairs(String productId) throws CorruptedEntityException{
        List<ProductCharacteristic> characteristics = productCharacteristicRepository.findByProductId(productId);
        return characteristics.stream()
                .map(characteristic -> {
                    CharacteristicDefinition definition = characteristicDefinitionRepository
                            .findById(characteristic.getCharacteristicDefinitionId())
                            .orElseThrow(() -> new CorruptedEntityException("ProductCharacteristic", productId,
                                    "CharacteristicDefinition", characteristic.getCharacteristicDefinitionId()));
                    return Map.entry(definition, characteristic);
                })
                .collect(Collectors.toSet());
    }
}