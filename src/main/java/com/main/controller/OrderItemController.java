package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.OrderItemResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.main.model.dto.request.OrderItemRequestDTO;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "OrderItemController", description = "Endpoints to order item")
public interface OrderItemController {

    @Operation(summary = "Create order item", responses = { @ApiResponse(responseCode = "201", description = "Created.") })
    ResponseEntity<ResponseDTO<OrderItemResponseDTO>> create(@RequestBody @Valid OrderItemRequestDTO request);

    @Operation(summary = "Update order item by id", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<OrderItemResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid OrderItemRequestDTO request);

    @Operation(summary = "Find order item by identificator", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<OrderItemResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all order itens", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<List<OrderItemResponseDTO>>> findAll();

    @Operation(summary = "Delete order item by id", responses = { @ApiResponse(responseCode = "204", description = "No content.") })
    ResponseEntity<Void> delete(@PathVariable UUID id);

    @Operation(summary = "Cancel order item", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<OrderItemResponseDTO>> cancel(@PathVariable UUID id);
}
