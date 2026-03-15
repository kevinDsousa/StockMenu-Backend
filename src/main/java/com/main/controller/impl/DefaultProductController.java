package com.main.controller.impl;

import com.main.controller.ProductController;
import com.main.infrastructure.generic.controller.ControllerResponseHelper;
import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.request.ProductRequestDTO;
import com.main.model.dto.response.ProductResponseDTO;
import com.main.service.ProductService;
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
@RequestMapping(value = "product")
@RequiredArgsConstructor
public class DefaultProductController implements ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<ProductResponseDTO>> create(@RequestBody @Valid ProductRequestDTO request) {
        return ControllerResponseHelper.created(service.create(request), MessageCommonsConstants.SAVE_SUCCESS.getValue());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid ProductRequestDTO request) {
        return ControllerResponseHelper.ok(service.update(id, request), MessageCommonsConstants.UPDATE_SUCCESS.getValue());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductResponseDTO>> findById(@PathVariable UUID id) {
        return ControllerResponseHelper.ok(service.findById(id), MessageCommonsConstants.FIND_ID_SUCCESS.getValue());
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ProductResponseDTO>>> findAll(@RequestParam(required = false) UUID companyId) {
        List<ProductResponseDTO> list = companyId != null ? service.findByCompanyId(companyId) : service.findAll();
        return ControllerResponseHelper.ok(list, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ControllerResponseHelper.noContent();
    }
}
