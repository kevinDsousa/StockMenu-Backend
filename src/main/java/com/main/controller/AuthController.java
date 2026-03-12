package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.request.LoginRequestDTO;
import com.main.model.dto.response.LoginResponseDTO;
import com.main.service.AuthService;
import com.main.utils.constants.MessageCommonsConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Tag(name = "AuthController", description = "Autenticação (login e JWT)")
@RestController
@RequestMapping(value = "auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica com email e senha e retorna o token JWT e dados do usuário.")
    public ResponseEntity<ResponseDTO<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDTO.fromData(response, HttpStatus.OK, MessageCommonsConstants.LOGIN_SUCCESS.getValue()));
    }
}
