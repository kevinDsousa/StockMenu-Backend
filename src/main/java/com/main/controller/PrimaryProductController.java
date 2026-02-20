package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.PrimaryProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Tag(name = "PrimaryProductController", description = "Endpoints to primary products")
public interface PrimaryProductController {

    @Operation(summary = "Find company by identificator", responses = {
            @ApiResponse(responseCode = "200", description = "Success."
            )})
    ResponseEntity<ResponseDTO<PrimaryProductResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all companies", responses = {
            @ApiResponse(responseCode = "200", description = "Success."
            )})
    ResponseEntity<ResponseDTO<List<PrimaryProductResponseDTO>>> findAll();
}
