package com.company.servicedesk.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateCallRequestDTO(@NotNull LocalDateTime createdAt,
                                   @NotBlank String description) {
}
