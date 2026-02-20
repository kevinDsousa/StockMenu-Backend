package com.main.controller.impl;

import com.main.controller.SubscriptionController;
import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.SubscriptionResponseDTO;
import com.main.service.SubscriptionService;
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
@RequestMapping(value = "subscription")
@RequiredArgsConstructor
public class DefaultSubscriptionController implements SubscriptionController {

    private final SubscriptionService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubscriptionResponseDTO>> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(service.findById(id), HttpStatus.OK, MessageCommonsConstants.FIND_ID_SUCCESS.getValue()));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<SubscriptionResponseDTO>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(service.findAll(), HttpStatus.OK, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue()));
    }
}
