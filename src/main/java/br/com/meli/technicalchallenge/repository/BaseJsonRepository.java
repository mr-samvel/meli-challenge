package br.com.meli.technicalchallenge.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Abstract base repository for loading and managing entities from JSON files.
 * Uses the Template Method pattern to provide a reusable JSON parsing mechanism
 * while allowing subclasses to customize specific behaviors.
 *
 * <p>This class automatically loads data from a JSON file on application startup
 * (@PostConstruct) and stores it in an in-memory map indexed by entity ID for fast lookups.
 *
 * <p>Subclasses must implement:
 * <ul>
 *   <li>{@link #getFileName()} - Name of the JSON file to load</li>
 *   <li>{@link #getTypeReference()} - Jackson TypeReference for deserialization</li>
 *   <li>{@link #getKeyExtractor()} - Function to extract ID from entity</li>
 * </ul>
 *
 * @param <T>  the entity type
 * @param <ID> the entity ID type
 */
public abstract class BaseJsonRepository<T, ID> {

    protected final ResourceLoader resourceLoader;
    protected final ObjectMapper objectMapper;
    protected final String dataPath;

    protected Map<ID, T> dataMap;

    /**
     * Constructs a BaseJsonRepository with required dependencies.
     *
     * @param resourceLoader Spring's ResourceLoader for loading JSON files
     * @param objectMapper   Jackson ObjectMapper for JSON deserialization
     * @param dataPath       Base path for JSON data files (from application.data.path property)
     */
    public BaseJsonRepository(ResourceLoader resourceLoader, ObjectMapper objectMapper,
                              @Value("${application.data.path}") String dataPath) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        this.dataPath = dataPath;
    }

    /**
     * Loads data from the JSON file into memory on application startup.
     * This method is automatically called after dependency injection is complete (@PostConstruct).
     *
     * <p>Can be overridden by subclasses to customize loading behavior
     * (e.g., filtering entities by type).
     *
     * @throws IOException if the JSON file cannot be read or parsed
     */
    @PostConstruct
    public void loadData() throws IOException {
        Resource resource = resourceLoader.getResource(dataPath + getFileName());
        List<T> dataList = objectMapper.readValue(resource.getInputStream(), getTypeReference());
        this.dataMap = dataList.stream()
                .collect(Collectors.toMap(getKeyExtractor(), Function.identity()));
    }

    /**
     * Returns the name of the JSON file to load (e.g., "products.json").
     *
     * @return the JSON file name
     */
    protected abstract String getFileName();

    /**
     * Returns the Jackson TypeReference for deserializing the JSON array.
     *
     * <p>Example implementation:
     * <pre>{@code
     * @Override
     * protected TypeReference<List<Product>> getTypeReference() {
     *     return new TypeReference<List<Product>>() {};
     * }
     * }</pre>
     *
     * @return TypeReference for List of entities
     */
    protected abstract TypeReference<List<T>> getTypeReference();

    /**
     * Returns a function that extracts the ID from an entity.
     *
     * <p>Example implementation:
     * <pre>{@code
     * @Override
     * protected Function<Product, String> getKeyExtractor() {
     *     return Product::getId;
     * }
     * }</pre>
     *
     * @return Function that extracts the ID from an entity
     */
    protected abstract Function<T, ID> getKeyExtractor();

    /**
     * Returns all entities as an immutable list.
     *
     * @return an immutable copy of all entities
     */
    protected List<T> findAll() {
        return List.copyOf(dataMap.values());
    }
}
