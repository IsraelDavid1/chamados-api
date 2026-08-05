package com.company.servicedesk.dtos;

import com.company.servicedesk.models.CallRequestState;

import java.time.LocalDateTime;
import java.util.UUID;

public record CallRequestResponseDTO(UUID userId,
                                     LocalDateTime createdAt,
                                     String description,
                                     CallRequestState state) {
}
