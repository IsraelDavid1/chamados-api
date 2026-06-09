package com.company.servicedesk.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FinishCallDTO(String solution,
                            @NotNull LocalDateTime endDate) {
}
