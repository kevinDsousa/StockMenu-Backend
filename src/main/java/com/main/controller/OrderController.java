package com.main.controller;

import com.main.infrastructure.generic.model.dto.PagedResponseDTO;
import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.OrderResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.main.model.dto.request.OrderCloseBatchRequestDTO;
import com.main.model.dto.request.OrderRequestDTO;
import com.main.model.dto.request.OrderTransferRequestDTO;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "OrderController", description = "Endpoints to order")
public interface OrderController {

    @Operation(summary = "Create order", responses = { @ApiResponse(responseCode = "201", description = "Created.") })
    ResponseEntity<ResponseDTO<OrderResponseDTO>> create(@RequestBody @Valid OrderRequestDTO request);

    @Operation(summary = "Update order by id", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<OrderResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid OrderRequestDTO request);

    @Operation(summary = "Find order by identificator", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<OrderResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all orders (list) or paged by company", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<List<OrderResponseDTO>>> findAll(@RequestParam(required = false) UUID companyId);

    @Operation(summary = "Find orders page by company with optional search", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<PagedResponseDTO<OrderResponseDTO>>> findPage(@RequestParam UUID companyId,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "20") int size,
                                                                            @RequestParam(required = false) String tableSearch,
                                                                            @RequestParam(required = false) String customerSearch,
                                                                            @RequestParam(required = false) String itemSearch);

    @Operation(summary = "Delete order by id", responses = { @ApiResponse(responseCode = "204", description = "No content.") })
    ResponseEntity<Void> delete(@PathVariable UUID id);

    @Operation(summary = "Transfer order to another table", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<OrderResponseDTO>> transfer(@RequestBody @Valid OrderTransferRequestDTO request);

    @Operation(summary = "Close (invoice) multiple orders in one request", responses = { @ApiResponse(responseCode = "204", description = "No content.") })
    ResponseEntity<Void> closeBatch(@RequestBody @Valid OrderCloseBatchRequestDTO request);
}
