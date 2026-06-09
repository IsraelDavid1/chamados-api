package com.company.servicedesk.dtos;

import com.company.servicedesk.models.Assets;
import com.company.servicedesk.models.AssetsType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateCallDTO(@NotNull LocalDateTime beginDate,
                            @NotBlank String techLogin,
                            @NotNull Assets asset,
                            @NotNull AssetsType assetType,
                            String department,
                            @NotBlank String firstAnalysis) {
}
