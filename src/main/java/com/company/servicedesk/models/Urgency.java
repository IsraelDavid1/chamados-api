package com.company.servicedesk.models;

import lombok.Getter;

@Getter
public enum Urgency {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high"),
    CRITICAL("critical");

    private final String urgency;

    Urgency(String urgency) { this.urgency = urgency; }
}
