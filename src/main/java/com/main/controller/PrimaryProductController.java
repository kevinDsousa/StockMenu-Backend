package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.PrimaryProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.main.model.dto.request.PrimaryProductRequestDTO;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "PrimaryProductController", description = "Endpoints to primary products")
public interface PrimaryProductController {

    @Operation(summary = "Create primary product", responses = { @ApiResponse(responseCode = "201", description = "Created.") })
    ResponseEntity<ResponseDTO<PrimaryProductResponseDTO>> create(@RequestBody @Valid PrimaryProductRequestDTO request);

    @Operation(summary = "Update primary product by id", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<PrimaryProductResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid PrimaryProductRequestDTO request);

    @Operation(summary = "Find primary product by identificator", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<PrimaryProductResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all primary products", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<List<PrimaryProductResponseDTO>>> findAll(@RequestParam(required = false) UUID companyId);

    @Operation(summary = "Delete primary product by id", responses = { @ApiResponse(responseCode = "204", description = "No content.") })
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
