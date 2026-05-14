package com.company.servicedesk.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationDTO(@NotBlank String userLogin,
                                @NotBlank @Size(min = 8, max = 20) String password) {
}
