package com.main.controller.impl;

import com.main.controller.OrderItemController;
import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.CompanyResponseDTO;
import com.main.model.dto.response.OrderItemResponseDTO;
import com.main.service.OrderItemService;
import com.main.utils.constants.MessageCommonsConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "orderItem")
@RequiredArgsConstructor
public class DefaultOrderItemController implements OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrderItemResponseDTO>> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(orderItemService.findById(id), HttpStatus.OK, MessageCommonsConstants.FIND_ID_SUCCESS.getValue()));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<OrderItemResponseDTO>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(orderItemService.findAll(), HttpStatus.OK, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue()));
    }
}
