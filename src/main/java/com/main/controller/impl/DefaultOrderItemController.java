package com.main.controller.impl;

import com.main.controller.OrderItemController;
import com.main.infrastructure.generic.controller.ControllerResponseHelper;
import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.request.OrderItemRequestDTO;
import com.main.model.dto.response.OrderItemResponseDTO;
import com.main.service.OrderItemService;
import com.main.utils.constants.MessageCommonsConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "orderItem")
@RequiredArgsConstructor
public class DefaultOrderItemController implements OrderItemController {

    private final OrderItemService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<OrderItemResponseDTO>> create(@RequestBody @Valid OrderItemRequestDTO request) {
        return ControllerResponseHelper.created(service.create(request), MessageCommonsConstants.SAVE_SUCCESS.getValue());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderItemResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid OrderItemRequestDTO request) {
        return ControllerResponseHelper.ok(service.update(id, request), MessageCommonsConstants.UPDATE_SUCCESS.getValue());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderItemResponseDTO>> findById(@PathVariable UUID id) {
        return ControllerResponseHelper.ok(service.findById(id), MessageCommonsConstants.FIND_ID_SUCCESS.getValue());
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<OrderItemResponseDTO>>> findAll() {
        return ControllerResponseHelper.ok(service.findAll(), MessageCommonsConstants.FIND_ALL_SUCCESS.getValue());
    }

    @GetMapping(params = "orderId")
    public ResponseEntity<ResponseDTO<List<OrderItemResponseDTO>>> findByOrderId(@RequestParam UUID orderId) {
        return ControllerResponseHelper.ok(service.findByOrderId(orderId), MessageCommonsConstants.FIND_ALL_SUCCESS.getValue());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ControllerResponseHelper.noContent();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ResponseDTO<OrderItemResponseDTO>> cancel(@PathVariable UUID id) {
        return ControllerResponseHelper.ok(service.cancel(id), MessageCommonsConstants.UPDATE_SUCCESS.getValue());
    }
}
