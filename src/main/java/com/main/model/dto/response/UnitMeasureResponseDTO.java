package com.main.model.dto.response;

import com.main.infrastructure.generic.model.dto.GenericDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitMeasureResponseDTO extends GenericDTO {

    private String key;
    private String label;
    private boolean active;
}
