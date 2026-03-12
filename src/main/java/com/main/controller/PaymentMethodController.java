package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.PaymentMethodResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.main.model.dto.request.PaymentMethodRequestDTO;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "PaymentMethodController", description = "Endpoints to payment method")
public interface PaymentMethodController {

    @Operation(summary = "Create payment method", responses = { @ApiResponse(responseCode = "201", description = "Created.") })
    ResponseEntity<ResponseDTO<PaymentMethodResponseDTO>> create(@RequestBody @Valid PaymentMethodRequestDTO request);

    @Operation(summary = "Update payment method by id", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<PaymentMethodResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid PaymentMethodRequestDTO request);

    @Operation(summary = "Find payment method by identificator", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<PaymentMethodResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all payment methods", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<List<PaymentMethodResponseDTO>>> findAll();

    @Operation(summary = "Delete payment method by id", responses = { @ApiResponse(responseCode = "204", description = "No content.") })
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
