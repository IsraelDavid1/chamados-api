package com.company.servicedesk.models;

import lombok.Getter;

@Getter
public enum Assets {
    HARDWARE("hardware"),
    SOFTWARE("software"),
    CLOUD("cloud"),
    INTERNET("internet"),
    DATA("data");

    private final String asset;

    Assets(String asset) {
        this.asset = asset;
    }
}
