package com.company.servicedesk.dtos;

import com.company.servicedesk.models.Departments;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(@NotBlank String userLogin,
                          @NotBlank @Size(min = 8, max = 20) String password,
                          @NotBlank Departments department) {
}
