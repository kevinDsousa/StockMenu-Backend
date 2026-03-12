package com.main.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import com.main.model.dto.request.UserRequestDTO;
import com.main.model.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "UserController", description = "Endpoints para usuários (acesso e garçons)")
public interface UserController {

    @Operation(summary = "Create user", responses = { @ApiResponse(responseCode = "201", description = "Created.") })
    ResponseEntity<ResponseDTO<UserResponseDTO>> create(@RequestBody @Valid UserRequestDTO request);

    @Operation(summary = "Update user by id", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<UserResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid UserRequestDTO request);

    @Operation(summary = "Find user by id", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<UserResponseDTO>> findById(@PathVariable UUID id);

    @Operation(summary = "Find all users", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<List<UserResponseDTO>>> findAll();

    @Operation(summary = "Find users by company", responses = { @ApiResponse(responseCode = "200", description = "Success.") })
    ResponseEntity<ResponseDTO<List<UserResponseDTO>>> findByCompanyId(@PathVariable UUID companyId);

    @Operation(summary = "Delete user by id", responses = { @ApiResponse(responseCode = "204", description = "No content.") })
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
