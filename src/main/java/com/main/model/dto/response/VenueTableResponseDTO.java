package com.main.model.dto.response;

import com.main.infrastructure.generic.model.dto.GenericDTO;
import com.main.model.enums.TableStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenueTableResponseDTO extends GenericDTO {

    private Integer tableNumber;
    private Integer capacity;
    private TableStatus status;
    private boolean active;

    public boolean isAvailable() {
        return TableStatus.FREE.equals(this.status) && active;
    }
}