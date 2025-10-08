package br.com.meli.technicalchallenge.exception.meliexception;

import br.com.meli.technicalchallenge.model.dto.response.ErrorResponse;
import org.springframework.http.HttpStatusCode;

/**
 * Base class for any exception thrown out of the system.
 * Automatically logs the error for traceability and provides the related ErrorResponse.
 *
 * <p>Subclasses must implement:
 * <ul>
 *   <li>{@link #getErrorTrace()} - Detailed internal error information for logging</li>
 *   <li>{@link #getExposableErrorMessage()} - User-safe error message</li>
 *   <li>{@link #getHttpStatus()} - HTTP status code for the response</li>
 * </ul>
 */
public abstract class MeLiException extends RuntimeException {
    /**
     * Constructs a new MeLiException and logs it.
     */
    public MeLiException() {
        this.logToFile();
    }

    /**
     * Constructs a new MeLiException with a cause and logs it.
     *
     * @param cause the underlying cause of this exception
     */
    public MeLiException(Throwable cause) {
        super(cause);
        this.logToFile();
    }

    /**
     * Logs the error trace for monitoring and debugging.
     * <p>Note: This is a placeholder implementation using System.out.
     * Can be replaced with proper logging framework.
     */
    protected void logToFile() {
        System.out.println(this.getErrorTrace());
    }

    /**
     * Builds the ErrorResponse to be sent to the client.
     *
     * @return ErrorResponse containing the HTTP status code and user-safe message
     */
    public ErrorResponse getErrorResponse() {
        return ErrorResponse.builder()
                .status(getHttpStatus())
                .message(getExposableErrorMessage())
                .build();
    }

    /**
     * Returns a detailed error trace for internal logging and debugging.
     * <p>This message may contain sensitive information and should NOT be exposed to clients.
     *
     * @return detailed error trace string
     */
    public abstract String getErrorTrace();

    /**
     * Returns a user-safe error message that can be exposed to clients.
     * <p>This message should not contain sensitive information like stack traces or system details.
     *
     * @return user-safe error message
     */
    public abstract String getExposableErrorMessage();

    /**
     * Returns the HTTP status code for this exception.
     *
     * @return HTTP status code
     */
    public abstract HttpStatusCode getHttpStatus();
}
