package br.com.meli.technicalchallenge.exception.meliexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Arrays;

/**
 * Base class for unmapped runtime exceptions (HTTP status >= 500).
 * <p>These exceptions represent unexpected system errors, infrastructure failures,
 * or any unhandled exceptions that occur during request processing.
 *
 * <p>Returns HTTP 500 (Internal Server Error) and a generic user-facing message.
 */
public class MeLiRuntimeException extends MeLiException {
    public MeLiRuntimeException() {
        super();
    }

    public MeLiRuntimeException(Throwable cause) {
        super(cause);
    }

    @Override
    public HttpStatusCode getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    
    @Override
    public String getErrorTrace() {
        return Arrays.toString(getStackTrace());
    }

    @Override
    public String getExposableErrorMessage() {
        return "An unknown error has occurred";
    }
}