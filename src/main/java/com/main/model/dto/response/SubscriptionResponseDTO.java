package com.main.model.dto.response;

import com.main.infrastructure.generic.model.dto.GenericDTO;
import com.main.model.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class SubscriptionResponseDTO extends GenericDTO {
    private UUID id;
    private UUID companyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private SubscriptionStatus status;
    private BigDecimal amountPaid;

    private boolean expired;
    private long daysRemaining;
    private boolean hasAccess;

}