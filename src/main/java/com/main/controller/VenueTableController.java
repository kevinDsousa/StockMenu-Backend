package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.VenueTableResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Tag(name = "VenueTableController", description = "Endpoints to venue table")
public interface VenueTableController {

    @Operation(summary = "Find venue table by identificator", responses = {
            @ApiResponse(responseCode = "200", description = "Success."
            )})
    ResponseEntity<ResponseDTO<VenueTableResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all venue table", responses = {
            @ApiResponse(responseCode = "200", description = "Success."
            )})
    ResponseEntity<ResponseDTO<List<VenueTableResponseDTO>>> findAll();
}
