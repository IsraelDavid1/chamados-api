package com.company.servicedesk.dtos;

import com.company.servicedesk.models.Assets;
import com.company.servicedesk.models.AssetsType;
import com.company.servicedesk.models.CallState;
import com.company.servicedesk.models.Departments;

import java.time.LocalDateTime;
import java.util.UUID;

public record CallResponseDTO(
        UUID id,
        LocalDateTime beginDate,
        Assets asset,
        AssetsType assetsType,
        Departments department,
        String firstAnalysis,
        String solution,
        LocalDateTime endDate,
        CallState callState,
        UUID createdById,
        UUID assignedToId
) {}
