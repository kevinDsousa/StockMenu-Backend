package com.main.infrastructure.generic.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public abstract class GenericDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

