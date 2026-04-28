package com.company.servicedesk.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MonthDTO(@NotNull LocalDate beginDate,
                       @NotNull LocalDate lastDate) {
}
