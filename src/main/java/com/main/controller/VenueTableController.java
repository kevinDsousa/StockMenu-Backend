package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.VenueTableResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.main.model.dto.request.VenueTableMergeRequestDTO;
import com.main.model.dto.request.VenueTableRequestDTO;
import com.main.model.dto.request.VenueTableSplitRequestDTO;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "VenueTableController", description = "Endpoints to venue table")
public interface VenueTableController {

    @Operation(summary = "Create venue table", responses = { @ApiResponse(responseCode = "201", description = "Created.") })
    ResponseEntity<ResponseDTO<VenueTableResponseDTO>> create(@RequestBody @Valid VenueTableRequestDTO request);

    @Operation(summary = "Update venue table by id", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<VenueTableResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid VenueTableRequestDTO request);

    @Operation(summary = "Find venue table by identificator", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<VenueTableResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all venue table", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<List<VenueTableResponseDTO>>> findAll(@RequestParam(required = false) UUID companyId);

    @Operation(summary = "Delete venue table by id", responses = { @ApiResponse(responseCode = "204", description = "No content.") })
    ResponseEntity<Void> delete(@PathVariable UUID id);

    @Operation(summary = "Split table into multiple tables", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<VenueTableResponseDTO>> split(@PathVariable UUID id, @RequestBody @Valid VenueTableSplitRequestDTO request);

    @Operation(summary = "Merge multiple tables into one", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<VenueTableResponseDTO>> merge(@RequestBody @Valid VenueTableMergeRequestDTO request);
}
