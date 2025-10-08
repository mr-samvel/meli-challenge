
package br.com.meli.technicalchallenge.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Provides integration tests for the product module.
 * Currently, the tests only guarantee happy-path functionality and are therefore incomplete.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getProductDetail_shouldReturnProperProductDetailDTO() throws Exception {
        String validProductId = "product-1";

        mockMvc.perform(get("/api/products/{id}", validProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id").value(validProductId))
                .andExpect(jsonPath("$.title").value("Samsung Galaxy S23 Ultra 256GB"))
                .andExpect(jsonPath("$.description").value("Premium smartphone with advanced camera system, 6.8-inch Dynamic AMOLED display, and S Pen support"))
                .andExpect(jsonPath("$.price").value(5499.00f))
                .andExpect(jsonPath("$.stock").value(15))

                .andExpect(jsonPath("$.brand").exists())
                .andExpect(jsonPath("$.brand.id").value("brand-1"))
                .andExpect(jsonPath("$.brand.name").value("Samsung"))

                .andExpect(jsonPath("$.images").isArray())
                .andExpect(jsonPath("$.images", hasSize(3)))

                .andExpect(jsonPath("$.characteristics").isArray())
                .andExpect(jsonPath("$.characteristics", hasSize(7)))

                .andExpect(jsonPath("$.store").exists())
                .andExpect(jsonPath("$.store.id").value("store-1"))
                .andExpect(jsonPath("$.store.name").value("TechStore Brasil"))
                .andExpect(jsonPath("$.store.reputation").value(4.8f))

                .andExpect(jsonPath("$.shipping").exists())
                .andExpect(jsonPath("$.shipping.cost").value(15.90f))
                .andExpect(jsonPath("$.shipping.to").exists())
                .andExpect(jsonPath("$.shipping.to.zipCode").value("88050-500"));
    }

    @Test
    void getProductDetail_shouldReturn404NotFoundWithInvalidProductId() throws Exception {
        String invalidProductId = "INVALID-PRODUCT-999";

        mockMvc.perform(get("/api/products/{id}", invalidProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value(containsString("Entity not found")))
                .andExpect(jsonPath("$.message").value(containsString(invalidProductId)));
    }
}