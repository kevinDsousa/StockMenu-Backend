package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.UserRequestDTO;
import com.main.model.dto.response.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService extends GenericService<UserRequestDTO, UserResponseDTO> {

    List<UserResponseDTO> findByCompanyId(UUID companyId);
}
