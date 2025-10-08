package br.com.meli.technicalchallenge.controller;

import br.com.meli.technicalchallenge.model.dto.response.product.ProductDetailDTO;
import br.com.meli.technicalchallenge.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product management endpoints")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get product details",
            description = "Retrieves complete product information including brand, images, characteristics, store" +
                    " info, and shipping details for the logged user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDetailDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product ID",
                    content = @Content
            )
    })
    public ResponseEntity<ProductDetailDTO> getProductDetail(
            @Parameter(description = "Product ID", required = true, example = "0199bb55-661f-7f45-af8c-d1b013911a16")
            @PathVariable String id
    ) {
        ProductDetailDTO response = productService.getProductDetail(id);
        return ResponseEntity.ok(response);
    }
}