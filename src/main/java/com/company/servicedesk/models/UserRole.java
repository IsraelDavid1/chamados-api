package com.company.servicedesk.models;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("user"),
    TECH("tech"),
    ADMIN("admin");

    private final String role;

    UserRole(String role){
        this.role = role;
    }
}
