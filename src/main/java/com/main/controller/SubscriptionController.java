package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.SubscriptionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.main.model.dto.request.SubscriptionRequestDTO;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "SubscriptionController", description = "Endpoints to subscription")
public interface SubscriptionController {

    @Operation(summary = "Create subscription", responses = { @ApiResponse(responseCode = "201", description = "Created.") })
    ResponseEntity<ResponseDTO<SubscriptionResponseDTO>> create(@RequestBody @Valid SubscriptionRequestDTO request);

    @Operation(summary = "Update subscription by id", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<SubscriptionResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid SubscriptionRequestDTO request);

    @Operation(summary = "Find subscription by identificator", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<SubscriptionResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all subscriptions", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<List<SubscriptionResponseDTO>>> findAll();

    @Operation(summary = "Delete subscription by id", responses = { @ApiResponse(responseCode = "204", description = "No content.") })
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
