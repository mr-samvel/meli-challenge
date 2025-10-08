package br.com.meli.technicalchallenge.integration.exception;

import br.com.meli.technicalchallenge.controller.ProductController;
import br.com.meli.technicalchallenge.exception.GlobalExceptionHandler;
import br.com.meli.technicalchallenge.exception.meliexception.CorruptedEntityException;
import br.com.meli.technicalchallenge.exception.meliexception.EntityNotFoundException;
import br.com.meli.technicalchallenge.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Ensures that exceptions not explicitly handled are caught by the GlobalExceptionHandler and properly processed,
 * preventing the application from crashing and returning an error message to the user.
 */
@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void handlesUncaughtMeLiBusinessException() throws Exception {
        String nonExistentProductId = "PROD-999";
        when(productService.getProductDetail(anyString()))
                .thenThrow(new EntityNotFoundException(nonExistentProductId, "Product"));

        mockMvc.perform(get("/api/products/{id}", nonExistentProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(jsonPath("$.message").value(containsString("Entity not found")))
                .andExpect(jsonPath("$.message").value(containsString(nonExistentProductId)));
    }

    @Test
    void handlesUncaughtMeLiRuntimeException() throws Exception {
        String productId = "PROD-001";
        CorruptedEntityException exception = new CorruptedEntityException(
                "Product", productId, "Brand", "BRAND-999");
        when(productService.getProductDetail(anyString())).thenThrow(exception);

        mockMvc.perform(get("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadGateway())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_GATEWAY.name()))
                .andExpect(jsonPath("$.message").value(containsString("An internal error occurred")));
    }

    @Test
    void convertsRuntimeExceptionToMeLiRuntimeException() throws Exception {
        String productId = "PROD-001";
        when(productService.getProductDetail(anyString()))
                .thenThrow(new RuntimeException("Unexpected runtime error"));

        mockMvc.perform(get("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.name()))
                .andExpect(jsonPath("$.message").value(containsString("An unknown error has occurred")));
    }
}