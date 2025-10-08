
package br.com.meli.technicalchallenge.unit.exception;

import br.com.meli.technicalchallenge.exception.GlobalExceptionHandler;
import br.com.meli.technicalchallenge.exception.meliexception.EntityNotFoundException;
import br.com.meli.technicalchallenge.exception.meliexception.MeLiRuntimeException;
import br.com.meli.technicalchallenge.model.dto.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleMeLiException_createsDynamicResponseEntity() {
        String entityId = "PROD-001";
        String entityType = "Product";
        EntityNotFoundException exception = new EntityNotFoundException(entityId, entityType);

        ErrorResponse expected = exception.getErrorResponse();
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMeLiException(exception);
        ErrorResponse actual = response.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(expected.getStatus());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void handleGenericException_shouldConvertRuntimeExceptionToMeLiRuntimeException() {
        RuntimeException originalException = new RuntimeException("Runtime error");

        assertThat(globalExceptionHandler.handleGenericException(originalException))
                .isNotNull()
                .isInstanceOf(ResponseEntity.class);
    }

    @Test
    void handleGenericException_shouldHandleExceptionWithNullCause() {
        Exception exceptionWithNullCause = new Exception("Exception without cause");

        assertThat(globalExceptionHandler.handleGenericException(exceptionWithNullCause))
                .isNotNull()
                .isInstanceOf(ResponseEntity.class);
    }
}