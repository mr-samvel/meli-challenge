package br.com.meli.technicalchallenge.unit.repository;

import br.com.meli.technicalchallenge.testutil.GenericEntityJsonRepository;
import br.com.meli.technicalchallenge.testutil.TestEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BaseJsonRepository functionality using a generic test entity.
 * Tests verify the template method pattern implementation and data loading behavior.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class GenericEntityJsonRepositoryTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;

    private GenericEntityJsonRepository repository;

    @BeforeEach
    void setUp() {
        repository = new GenericEntityJsonRepository(resourceLoader, objectMapper, "classpath:data/");
    }

    @Test
    void loadData_shouldLoadValidJsonSuccessfully() throws IOException {
        repository.loadData();

        List<TestEntity> entities = repository.findAll();
        assertNotNull(entities);
        assertEquals(3, entities.size());
    }

    @Test
    void loadData_shouldPopulateDataMapCorrectly() throws IOException {
        repository.loadData();

        Optional<TestEntity> entity1 = repository.findById("test-1");
        Optional<TestEntity> entity2 = repository.findById("test-2");
        Optional<TestEntity> entity3 = repository.findById("test-3");

        assertTrue(entity1.isPresent());
        assertTrue(entity2.isPresent());
        assertTrue(entity3.isPresent());

        assertEquals("Entity One", entity1.get().getName());
        assertEquals(100, entity1.get().getValue());

        assertEquals("Entity Two", entity2.get().getName());
        assertEquals(200, entity2.get().getValue());

        assertEquals("Entity Three", entity3.get().getName());
        assertEquals(300, entity3.get().getValue());
    }

    @Test
    void findById_shouldReturnEntityWhenExists() throws IOException {
        repository.loadData();

        Optional<TestEntity> result = repository.findById("test-2");

        assertTrue(result.isPresent());
        assertEquals("test-2", result.get().getId());
        assertEquals("Entity Two", result.get().getName());
        assertEquals(200, result.get().getValue());
    }

    @Test
    void findById_shouldReturnEmptyWhenNotFound() throws IOException {
        repository.loadData();

        Optional<TestEntity> result = repository.findById("non-existent-id");

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllLoadedEntities() throws IOException {
        repository.loadData();

        List<TestEntity> entities = repository.findAll();

        assertNotNull(entities);
        assertEquals(3, entities.size());
        assertTrue(entities.stream().anyMatch(e -> e.getId().equals("test-1")));
        assertTrue(entities.stream().anyMatch(e -> e.getId().equals("test-2")));
        assertTrue(entities.stream().anyMatch(e -> e.getId().equals("test-3")));
    }

    @Test
    void findAll_shouldReturnImmutableCopy() throws IOException {
        repository.loadData();

        List<TestEntity> entities = repository.findAll();

        assertThrows(UnsupportedOperationException.class, () -> {
            entities.add(new TestEntity("new-id", "New Entity", 999));
        });
    }

    @Test
    void loadData_shouldHandleEmptyJsonArray() {
        GenericEntityJsonRepository emptyRepository = new GenericEntityJsonRepository(
                resourceLoader, objectMapper, "classpath:data/") {
            @Override
            public String getFileName() {
                return "test_entities_empty.json";
            }
        };

        assertDoesNotThrow(() -> emptyRepository.loadData());

        List<TestEntity> entities = emptyRepository.findAll();
        assertNotNull(entities);
        assertTrue(entities.isEmpty());
    }

    @Test
    void loadData_shouldThrowIOExceptionWhenFileNotFound() {
        GenericEntityJsonRepository invalidRepository = new GenericEntityJsonRepository(
                resourceLoader, objectMapper, "classpath:data/") {
            @Override
            public String getFileName() {
                return "non_existent_file.json";
            }
        };

        assertThrows(IOException.class, () -> invalidRepository.loadData());
    }

    @Test
    void loadData_shouldThrowExceptionWhenJsonIsMalformed() {
        GenericEntityJsonRepository invalidRepository = new GenericEntityJsonRepository(
                resourceLoader, objectMapper, "classpath:data/") {
            @Override
            public String getFileName() {
                return "test_entities_invalid.json";
            }
        };

        assertThrows(IOException.class, () -> invalidRepository.loadData());
    }

    @Test
    void loadData_shouldThrowExceptionOnDuplicateIds() {
        GenericEntityJsonRepository duplicateRepository = new GenericEntityJsonRepository(
                resourceLoader, objectMapper, "classpath:data/") {
            @Override
            public String getFileName() {
                return "test_entities_duplicate_ids.json";
            }
        };

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            duplicateRepository.loadData();
        });

        assertTrue(exception.getMessage().contains("Duplicate key"));
    }

    @Test
    void templateMethodPattern_shouldUseCorrectFileName() {
        String fileName = repository.getFileName();

        assertEquals("test_entities.json", fileName);
    }

    @Test
    void dataMap_shouldBeAccessibleAfterLoad() throws IOException {
        repository.loadData();

        Optional<TestEntity> entity = repository.findById("test-1");

        assertTrue(entity.isPresent());
        assertEquals("test-1", entity.get().getId());
    }
}