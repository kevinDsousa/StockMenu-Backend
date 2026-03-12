package com.main.controller.impl;

import com.main.controller.VenueTableController;
import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.VenueTableResponseDTO;
import com.main.service.VenueTableService;
import com.main.utils.constants.MessageCommonsConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

import com.main.model.dto.request.VenueTableMergeRequestDTO;
import com.main.model.dto.request.VenueTableRequestDTO;
import com.main.model.dto.request.VenueTableSplitRequestDTO;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "venueTable")
@RequiredArgsConstructor
public class DefaultVenueTableController implements VenueTableController {

    private final VenueTableService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<VenueTableResponseDTO>> create(@RequestBody @Valid VenueTableRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.fromData(service.create(request), HttpStatus.CREATED, MessageCommonsConstants.SAVE_SUCCESS.getValue()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<VenueTableResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid VenueTableRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(service.update(id, request), HttpStatus.OK, MessageCommonsConstants.UPDATE_SUCCESS.getValue()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<VenueTableResponseDTO>> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(service.findById(id), HttpStatus.OK, MessageCommonsConstants.FIND_ID_SUCCESS.getValue()));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<VenueTableResponseDTO>>> findAll(@RequestParam(required = false) UUID companyId) {
        List<VenueTableResponseDTO> list = companyId != null ? service.findByCompanyId(companyId) : service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(list, HttpStatus.OK, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/split")
    public ResponseEntity<ResponseDTO<VenueTableResponseDTO>> split(@PathVariable UUID id, @RequestBody @Valid VenueTableSplitRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(service.split(id, request), HttpStatus.OK, MessageCommonsConstants.UPDATE_SUCCESS.getValue()));
    }

    @PostMapping("/merge")
    public ResponseEntity<ResponseDTO<VenueTableResponseDTO>> merge(@RequestBody @Valid VenueTableMergeRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(service.merge(request), HttpStatus.OK, MessageCommonsConstants.UPDATE_SUCCESS.getValue()));
    }
}
