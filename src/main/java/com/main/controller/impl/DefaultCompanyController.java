package com.main.controller.impl;

import com.main.controller.CompanyController;
import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.CompanyResponseDTO;
import com.main.service.CompanyService;
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
@RequestMapping(value = "company")
@RequiredArgsConstructor
public class DefaultCompanyController implements CompanyController {

    private final CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CompanyResponseDTO>> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(companyService.findById(id), HttpStatus.OK, MessageCommonsConstants.FIND_ID_SUCCESS.getValue()));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<CompanyResponseDTO>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.fromData(companyService.findAll(), HttpStatus.OK, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue()));
    }
}
