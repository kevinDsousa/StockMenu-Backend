package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.main.model.dto.request.ProductRequestDTO;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "ProductController", description = "Endpoints to product")
public interface ProductController {

    @Operation(summary = "Create product", responses = { @ApiResponse(responseCode = "201", description = "Created.") })
    ResponseEntity<ResponseDTO<ProductResponseDTO>> create(@RequestBody @Valid ProductRequestDTO request);

    @Operation(summary = "Update product by id", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<ProductResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid ProductRequestDTO request);

    @Operation(summary = "Find product by identificator", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<ProductResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all products", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<List<ProductResponseDTO>>> findAll(@RequestParam(required = false) UUID companyId);

    @Operation(summary = "Delete product by id", responses = { @ApiResponse(responseCode = "204", description = "No content.") })
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
