package com.main.model.dto.response;

import com.main.infrastructure.generic.model.dto.GenericDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponseDTO extends GenericDTO {

    private String tradeName;
    private String corporateName;
    private String cnpj;
    private String whatsapp;
    private boolean active;

    private boolean canOperate;
}