package com.company.servicedesk.dtos;

import com.company.servicedesk.models.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateCallDTO(@NotNull LocalDateTime beginDate,
                            @NotBlank String techLogin,
                            @NotNull Assets asset,
                            @NotNull AssetsType assetType,
                            Departments department,
                            @NotBlank String firstAnalysis,
                            Urgency urgency,
                            Impact impact) {
}
