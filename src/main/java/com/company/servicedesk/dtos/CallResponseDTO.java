package com.company.servicedesk.dtos;

import com.company.servicedesk.models.Assets;
import com.company.servicedesk.models.AssetsType;
import com.company.servicedesk.models.CallState;

import java.time.LocalDate;
import java.util.UUID;

public record CallResponseDTO(
        UUID id,
        LocalDate beginDate,
        Assets asset,
        AssetsType assetsType,
        String department,
        String firstAnalysis,
        String solution,
        LocalDate endDate,
        CallState callState,
        UUID createdById,
        UUID assignedToId
) {}
