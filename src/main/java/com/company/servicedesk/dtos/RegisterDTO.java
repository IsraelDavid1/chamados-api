package com.company.servicedesk.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(@NotBlank String userLogin,
                          @NotBlank @Size(min = 8, max = 20) String password,
                          @NotBlank String departament) {
}
