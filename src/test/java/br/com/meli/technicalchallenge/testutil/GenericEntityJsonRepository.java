package br.com.meli.technicalchallenge.testutil;

import br.com.meli.technicalchallenge.repository.BaseJsonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Generic repository implementation for testing BaseJsonRepository behavior.
 * Provides minimal implementation of template methods to validate base functionality.
 */
public class GenericEntityJsonRepository extends BaseJsonRepository<TestEntity, String> {

    public GenericEntityJsonRepository(ResourceLoader resourceLoader, ObjectMapper objectMapper,
                                       @Value("${application.data.path}") String dataPath) {
        super(resourceLoader, objectMapper, dataPath);
    }

    /**
     * Exposes getFileName() as public for testing.
     *
     * @return file name
     */
    @Override
    public String getFileName() {
        return "test_entities.json";
    }

    @Override
    protected TypeReference<List<TestEntity>> getTypeReference() {
        return new TypeReference<List<TestEntity>>() {};
    }

    @Override
    protected Function<TestEntity, String> getKeyExtractor() {
        return TestEntity::getId;
    }

    /**
     * Simple findById implementation for testing purposes.
     *
     * @param id the entity ID
     * @return Optional containing the entity if found
     */
    public Optional<TestEntity> findById(String id) {
        return Optional.ofNullable(dataMap.get(id));
    }

    /**
     * Exposes findAll() as public for testing.
     *
     * @return list of all entities
     */
    @Override
    public List<TestEntity> findAll() {
        return super.findAll();
    }
}