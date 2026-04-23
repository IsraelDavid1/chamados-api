package com.company.servicedesk.dtos;

import com.company.servicedesk.models.Assets;
import com.company.servicedesk.models.AssetsType;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateCallDTO(@NotBlank LocalDate beginDate,
                            @NotBlank Assets asset,
                            @NotBlank AssetsType assetType,
                            String department,
                            String firstAnalysis) {
}
