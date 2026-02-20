package com.main.model.dto.response;

import com.main.infrastructure.generic.model.dto.GenericDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class PaymentMethodResponseDTO extends GenericDTO {
    private UUID id;
    private UUID companyId;
    private String name;
    private boolean active;
    private boolean allowsDelivery;
    private boolean onlinePayment;
}