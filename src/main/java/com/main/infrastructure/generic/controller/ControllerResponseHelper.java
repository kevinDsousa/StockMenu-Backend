package com.main.infrastructure.generic.controller;

import com.main.infrastructure.generic.model.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ControllerResponseHelper {

    private ControllerResponseHelper() {
    }

    public static <T> ResponseEntity<ResponseDTO<T>> created(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.fromData(data, HttpStatus.CREATED, message));
    }

    public static <T> ResponseEntity<ResponseDTO<T>> ok(T data, String message) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDTO.fromData(data, HttpStatus.OK, message));
    }

    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }
}
