package com.company.servicedesk.dtos;

import com.company.servicedesk.models.Assets;
import com.company.servicedesk.models.AssetsType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateCallDTO(@NotNull LocalDate beginDate,
                            @NotNull Assets asset,
                            @NotNull AssetsType assetType,
                            String department,
                            String firstAnalysis) {
}
