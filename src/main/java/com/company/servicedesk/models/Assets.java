package com.company.servicedesk.models;

import lombok.Getter;

@Getter
public enum Assets {
    HARDWARE("hardware"),
    SOFTWARE("software"),
    CLOUD("cloud"),
    NETWORK("network"),
    DATA("data"),
    DATABASE("database"),
    SERVER("server"),
    ACCOUNT("account"),
    IDENTITY("identity"),
    EMAIL("email"),
    SECURITY("security"),
    BACKUP("backup"),
    TELEPHONY("telephony"),
    PRINTING("printing"),
    APPLICATION("application"),
    OTHER("other");

    private final String asset;

    Assets(String asset) {
        this.asset = asset;
    }
}
