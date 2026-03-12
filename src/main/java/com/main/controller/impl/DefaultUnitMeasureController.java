package com.main.controller.impl;

import com.main.controller.UnitMeasureController;
import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.request.UnitMeasureRequestDTO;
import com.main.model.dto.response.UnitMeasureResponseDTO;
import com.main.service.UnitMeasureService;
import com.main.utils.constants.MessageCommonsConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/unitMeasure")
@RequiredArgsConstructor
public class DefaultUnitMeasureController implements UnitMeasureController {

    private final UnitMeasureService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<UnitMeasureResponseDTO>> create(@RequestBody @Valid UnitMeasureRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.fromData(service.create(request), HttpStatus.CREATED, MessageCommonsConstants.SAVE_SUCCESS.getValue()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UnitMeasureResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid UnitMeasureRequestDTO request) {
        return ResponseEntity.ok(ResponseDTO.fromData(service.update(id, request), HttpStatus.OK, MessageCommonsConstants.UPDATE_SUCCESS.getValue()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UnitMeasureResponseDTO>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ResponseDTO.fromData(service.findById(id), HttpStatus.OK, MessageCommonsConstants.FIND_ID_SUCCESS.getValue()));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<UnitMeasureResponseDTO>>> findAll() {
        return ResponseEntity.ok(ResponseDTO.fromData(service.findAll(), HttpStatus.OK, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
