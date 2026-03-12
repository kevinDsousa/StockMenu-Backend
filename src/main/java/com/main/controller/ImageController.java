package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.service.ImageService;
import com.main.utils.constants.MessageCommonsConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "ImageController", description = "Upload de imagens para o cardápio (MinIO)")
@RestController
@RequestMapping(value = "image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    @Operation(summary = "Upload de imagem", description = "Envia arquivo e retorna a chave (object key) para persistir em Product ou PrimaryProduct. Opcional: context (ex.: companyId) para prefixo no storage.")
    public ResponseEntity<ResponseDTO<String>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "context", required = false) String context) {
        String objectKey = imageService.uploadImage(file, context);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.fromData(objectKey, HttpStatus.CREATED, MessageCommonsConstants.UPLOAD_SUCCESS.getValue()));
    }
}
