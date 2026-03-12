package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.CompanyResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "CompanyController", description = "Endpoints to company")
public interface CompanyController {

    @Operation(summary = "Create company", responses = { @ApiResponse(responseCode = "201", description = "Created.") })
    ResponseEntity<ResponseDTO<CompanyResponseDTO>> create(@RequestBody @Valid com.main.model.dto.request.CompanyRequestDTO request);

    @Operation(summary = "Update company by id", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<CompanyResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid com.main.model.dto.request.CompanyRequestDTO request);

    @Operation(summary = "Find company by identificator", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<CompanyResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all companies", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<List<CompanyResponseDTO>>> findAll();

    @Operation(summary = "Delete company by id", responses = { @ApiResponse(responseCode = "204", description = "No content.") })
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
