package com.company.servicedesk.dtos;

import com.company.servicedesk.models.Assets;
import com.company.servicedesk.models.AssetsType;
import com.company.servicedesk.models.Departments;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateCompleteCallDTO(@NotNull LocalDateTime beginDate,
                                    @NotBlank String techLogin,
                                    @NotNull Assets asset,
                                    @NotNull AssetsType assetType,
                                    Departments department,
                                    @NotBlank String firstAnalysis,
                                    String solution,
                                    @NotNull LocalDateTime endDate) {
}
