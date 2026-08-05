package com.company.servicedesk.dtos;

import com.company.servicedesk.models.Assets;
import com.company.servicedesk.models.AssetsType;
import com.company.servicedesk.models.Departments;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ApproveRequestAndCreateCallDTO(@NotNull UUID requestId,
                                             String observation,
                                             @NotNull LocalDateTime approvalTime,
                                             @NotNull Departments departments,
                                             @NotNull Assets assets,
                                             @NotNull AssetsType assetsType) {
}
