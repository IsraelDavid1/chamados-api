package com.company.servicedesk.dtos;

import java.time.LocalDate;

public record FinishCallDTO(String solution,
                            LocalDate endDate) {
}
