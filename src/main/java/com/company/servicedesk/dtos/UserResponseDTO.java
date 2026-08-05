package com.company.servicedesk.dtos;

import com.company.servicedesk.models.Departments;
import com.company.servicedesk.models.UserRole;

import java.util.UUID;

public record UserResponseDTO(UUID id,
                              String login,
                              Departments department,
                              UserRole role) {
}
