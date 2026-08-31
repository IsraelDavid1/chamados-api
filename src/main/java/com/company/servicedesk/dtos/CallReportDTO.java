package com.company.servicedesk.dtos;

import com.company.servicedesk.models.*;


import java.time.LocalDateTime;

public record CallReportDTO(LocalDateTime beginDate,
                            String createdBy,
                            String assignedTo,
                            Assets asset,
                            AssetsType assetType,
                            Departments department,
                            String firstAnalysis,
                            String solution,
                            LocalDateTime endDate,
                            CallState callState,
                            Urgency urgency,
                            Impact impact) {
}
