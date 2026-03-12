package com.main.model.dto.response;

import com.main.infrastructure.generic.model.dto.GenericDTO;
import com.main.model.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserResponseDTO extends GenericDTO {

    private UUID companyId;
    private String email;
    private String name;
    private UserRole role;
    private boolean active;
}
