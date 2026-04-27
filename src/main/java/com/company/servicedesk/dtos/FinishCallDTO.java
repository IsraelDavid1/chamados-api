package com.company.servicedesk.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FinishCallDTO(String solution,
                            @NotNull LocalDate endDate) {
}
