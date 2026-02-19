package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.OrderItemResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Tag(name = "OrderItemController", description = "Endpoints to order item")
public interface OrderItemController {

    @Operation(summary = "Find order item by identificator", responses = {
            @ApiResponse(responseCode = "200", description = "Success."
            )})
    ResponseEntity<ResponseDTO<OrderItemResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all order itens", responses = {
            @ApiResponse(responseCode = "200", description = "Success."
            )})
    ResponseEntity<ResponseDTO<List<OrderItemResponseDTO>>> findAll();
}
