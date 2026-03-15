package com.main.controller.impl;

import com.main.controller.OrderController;
import com.main.infrastructure.generic.controller.ControllerResponseHelper;
import com.main.infrastructure.generic.model.dto.PagedResponseDTO;
import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.request.OrderCloseBatchRequestDTO;
import com.main.model.dto.request.OrderRequestDTO;
import com.main.model.dto.request.OrderTransferRequestDTO;
import com.main.model.dto.response.OrderResponseDTO;
import com.main.service.OrderService;
import com.main.utils.constants.MessageCommonsConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "order")
@RequiredArgsConstructor
public class DefaultOrderController implements OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<OrderResponseDTO>> create(@RequestBody @Valid OrderRequestDTO request) {
        return ControllerResponseHelper.created(service.create(request), MessageCommonsConstants.SAVE_SUCCESS.getValue());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid OrderRequestDTO request) {
        return ControllerResponseHelper.ok(service.update(id, request), MessageCommonsConstants.UPDATE_SUCCESS.getValue());
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<OrderResponseDTO>>> findAll(@RequestParam(required = false) UUID companyId) {
        List<OrderResponseDTO> list = companyId != null ? service.findByCompanyId(companyId) : service.findAll();
        return ControllerResponseHelper.ok(list, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue());
    }

    @GetMapping("/page")
    public ResponseEntity<ResponseDTO<PagedResponseDTO<OrderResponseDTO>>> findPage(
            @RequestParam UUID companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String tableSearch,
            @RequestParam(required = false) String customerSearch,
            @RequestParam(required = false) String itemSearch) {
        PagedResponseDTO<OrderResponseDTO> paged = service.findPageByCompanyId(companyId, page, size, tableSearch, customerSearch, itemSearch);
        return ControllerResponseHelper.ok(paged, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue());
    }

    @PostMapping("/transfer")
    public ResponseEntity<ResponseDTO<OrderResponseDTO>> transfer(@RequestBody @Valid OrderTransferRequestDTO request) {
        return ControllerResponseHelper.ok(service.transferOrderToTable(request.orderId(), request.targetTableId()), MessageCommonsConstants.UPDATE_SUCCESS.getValue());
    }

    @PostMapping("/close-batch")
    public ResponseEntity<Void> closeBatch(@RequestBody @Valid OrderCloseBatchRequestDTO request) {
        service.closeOrdersByIds(request);
        return ControllerResponseHelper.noContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderResponseDTO>> findById(@PathVariable UUID id) {
        return ControllerResponseHelper.ok(service.findById(id), MessageCommonsConstants.FIND_ID_SUCCESS.getValue());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ControllerResponseHelper.noContent();
    }
}
