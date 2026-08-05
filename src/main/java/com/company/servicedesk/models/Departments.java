package com.company.servicedesk.models;

import lombok.Getter;

@Getter
public enum Departments {
    CLEANING("cleaning"),
    CONTRACTS("contracts"),
    DIRECTORSHIP("directorship"),
    EVENTS("events"),
    FINANCE("finance"),
    FIRE_BRIGADE("fire_brigade"),
    HR("hr"),
    IT("it"),
    LEGAL("legal"),
    MAINTENANCE("maintenance"),
    MARKETING("marketing"),
    MERCHANDISING("merchandising"),
    OPERATIONS("operations"),
    PARKING("parking"),
    PURCHASING("purchasing"),
    SECURITY("security");

    private final String department;

    Departments(String department) { this.department = department; }
}
