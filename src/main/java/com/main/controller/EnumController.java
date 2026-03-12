package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.response.EnumItemDTO;
import com.main.model.dto.response.UnitMeasureResponseDTO;
import com.main.service.UnitMeasureService;
import com.main.utils.constants.MessageCommonsConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "EnumController", description = "Endpoints para listar enums (unidades de medida, etc.)")
@RestController
@RequestMapping("enum")
@RequiredArgsConstructor
public class EnumController {

    private final UnitMeasureService unitMeasureService;

    @GetMapping("/unitMeasure")
    @Operation(summary = "Listar unidades de medida", description = "Retorna as opções de unidade para insumos e produtos.")
    public ResponseEntity<ResponseDTO<List<EnumItemDTO>>> getUnitMeasures() {
        List<EnumItemDTO> list = unitMeasureService.findAll().stream()
                .map(r -> new EnumItemDTO(r.getKey(), r.getLabel()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                ResponseDTO.fromData(list, HttpStatus.OK, MessageCommonsConstants.FIND_ALL_SUCCESS.getValue()));
    }
}
