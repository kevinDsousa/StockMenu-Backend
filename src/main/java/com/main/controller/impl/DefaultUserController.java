package com.main.controller.impl;

import com.main.controller.UserController;
import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.request.UserRequestDTO;
import com.main.model.dto.response.UserResponseDTO;
import com.main.service.UserService;
import com.main.utils.constants.MessageCommonsConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "user")
@RequiredArgsConstructor
public class DefaultUserController implements UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<UserResponseDTO>> create(@RequestBody @Valid UserRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.fromData(service.create(request), HttpStatus.CREATED, MessageCommonsConstants.SAVE_SUCCESS.getValue()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid UserRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(service.update(id, request), HttpStatus.OK, MessageCommonsConstants.UPDATE_SUCCESS.getValue()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(service.findById(id), HttpStatus.OK, MessageCommonsConstants.FIND_ID_SUCCESS.getValue()));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(service.findAll(), HttpStatus.OK, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue()));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> findByCompanyId(@PathVariable UUID companyId) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(service.findByCompanyId(companyId), HttpStatus.OK, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
