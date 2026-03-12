package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.request.UnitMeasureRequestDTO;
import com.main.model.dto.response.UnitMeasureResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "UnitMeasureController", description = "CRUD de unidades de medida")
@RequestMapping("/unitMeasure")
public interface UnitMeasureController {

    @Operation(summary = "Criar unidade de medida")
    ResponseEntity<ResponseDTO<UnitMeasureResponseDTO>> create(@RequestBody @Valid UnitMeasureRequestDTO request);

    @Operation(summary = "Atualizar unidade de medida")
    ResponseEntity<ResponseDTO<UnitMeasureResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid UnitMeasureRequestDTO request);

    @Operation(summary = "Buscar unidade por ID")
    ResponseEntity<ResponseDTO<UnitMeasureResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Listar todas as unidades")
    ResponseEntity<ResponseDTO<List<UnitMeasureResponseDTO>>> findAll();

    @Operation(summary = "Excluir unidade")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
