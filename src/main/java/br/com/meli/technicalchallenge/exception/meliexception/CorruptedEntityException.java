package br.com.meli.technicalchallenge.exception.meliexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * Exception thrown when data integrity constraints are violated.
 * <p>This typically occurs when an entity references another entity that doesn't exist,
 * indicating corrupted data or referential integrity violations.
 *
 * <p>Since JSON files don't provide foreign key constraints like a relational database,
 * this exception is used to detect and report data consistency issues at runtime.
 *
 * <p>Returns HTTP 502 (Bad Gateway) to indicate an internal system error.
 * The user-facing message is intentionally generic to avoid exposing internal data structure details.
 */
public class CorruptedEntityException extends MeLiRuntimeException {
    private final String entityType;
    private final String entityId;
    private final String relatedType;
    private final String relatedId;

    /**
     * Constructs a new CorruptedEntityException.
     *
     * @param entityType the type of entity with the corrupted reference
     * @param entityId   the ID of the entity with the corrupted reference
     * @param relatedType the type of the missing related entity
     * @param relatedId  the ID of the missing related entity
     */
    public CorruptedEntityException(String entityType, String entityId, String relatedType, String relatedId) {
        super();
        this.entityType = entityType;
        this.entityId = entityId;
        this.relatedType = relatedType;
        this.relatedId = relatedId;
    }

    @Override
    public String getErrorTrace() {
        return String.format("CorruptedEntityException: %s with ID '%s' references non-existent %s with ID '%s'",
                entityType, entityId, relatedType, relatedId);
    }

    @Override
    public String getExposableErrorMessage() {
        return "An internal error occurred. Please try again later.";
    }

    @Override
    public HttpStatusCode getHttpStatus() {
        return HttpStatus.BAD_GATEWAY;
    }
}