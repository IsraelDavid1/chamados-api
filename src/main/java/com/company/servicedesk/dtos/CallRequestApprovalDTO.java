package com.company.servicedesk.dtos;

import com.company.servicedesk.models.CallRequestState;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CallRequestApprovalDTO(@NotNull UUID requestId,
                                     @NotNull CallRequestState callRequestState,
                                     String observation,
                                     @NotNull LocalDateTime approvalTime) {
}
