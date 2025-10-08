package br.com.meli.technicalchallenge.exception.meliexception;

/**
 * Base class for mapped business exceptions (400 <= HTTP status < 500).
 * <p>These exceptions represent client errors such as invalid input, resource not found,
 * unauthorized access, or business rule violations.
 *
 * <p>Subclasses should provide specific error messages and HTTP status codes
 * appropriate to the business error.
 */
public abstract class MeLiBusinessException extends MeLiException {
    public MeLiBusinessException() {
        super();
    }

    public MeLiBusinessException(Throwable cause) {
        super(cause);
    }
}