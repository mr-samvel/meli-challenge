package br.com.meli.technicalchallenge.exception.meliexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * Exception thrown when a requested entity cannot be found.
 * <p>Returns HTTP 404 (Not Found) status to indicate the resource doesn't exist.
 */
public class EntityNotFoundException extends MeLiBusinessException {
    private final String entityId;
    private final String entityType;

    /**
     * Constructs a new EntityNotFoundException.
     *
     * @param entityId   the ID of the entity that was not found
     * @param entityType the type of entity (e.g., "Product", "User")
     */
    public EntityNotFoundException(String entityId, String entityType) {
        super();
        this.entityId = entityId;
        this.entityType = entityType;
    }

    @Override
    public String getErrorTrace() {
        return String.format("EntityNotFoundException: Entity %s not found with ID: %s", entityType, entityId);
    }

    @Override
    public String getExposableErrorMessage() {
        return String.format("Entity not found with ID: %s", entityId);
    }

    @Override
    public HttpStatusCode getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}