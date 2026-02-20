package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.PaymentMethodResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Tag(name = "PaymentMethodController", description = "Endpoints to payment method")
public interface PaymentMethodController {

    @Operation(summary = "Find payment method by identificator", responses = {
            @ApiResponse(responseCode = "200", description = "Success."
            )})
    ResponseEntity<ResponseDTO<PaymentMethodResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all payment methods", responses = {
            @ApiResponse(responseCode = "200", description = "Success."
            )})
    ResponseEntity<ResponseDTO<List<PaymentMethodResponseDTO>>> findAll();
}
