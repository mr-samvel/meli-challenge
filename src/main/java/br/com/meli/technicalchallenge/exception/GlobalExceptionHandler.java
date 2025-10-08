package br.com.meli.technicalchallenge.exception;

import br.com.meli.technicalchallenge.exception.meliexception.MeLiException;
import br.com.meli.technicalchallenge.exception.meliexception.MeLiRuntimeException;
import br.com.meli.technicalchallenge.model.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Treats any non-catched exception and throws its related ErrorResponse as a request response.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MeLiException.class)
    public ResponseEntity<ErrorResponse> handleMeLiException(MeLiException ex) {
        ErrorResponse err = ex.getErrorResponse();
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) throws MeLiRuntimeException {
        return handleMeLiException(new MeLiRuntimeException(ex.getCause()));
    }
}