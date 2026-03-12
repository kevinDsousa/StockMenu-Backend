package com.main.service;

import com.main.model.dto.request.LoginRequestDTO;
import com.main.model.dto.response.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);
}
