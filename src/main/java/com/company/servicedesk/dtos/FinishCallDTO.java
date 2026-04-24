package com.company.servicedesk.dtos;

import com.company.servicedesk.models.Assets;
import com.company.servicedesk.models.AssetsType;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record FinishCallDTO(String solution,
                            LocalDate endDate) {
}
