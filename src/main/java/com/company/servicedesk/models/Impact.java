package com.company.servicedesk.models;

import lombok.Getter;

@Getter
public enum Impact {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high"),
    CRITICAL("critical");

    private final String impact;

    Impact(String impact) { this.impact = impact; }
}
