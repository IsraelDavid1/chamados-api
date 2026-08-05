package com.company.servicedesk.models;

import lombok.Getter;

@Getter
public enum CallRequestState {
    PENDING("pending"),
    APPROVED("approved"),
    DENIED("denied");

    private final String callRequestState;

    CallRequestState(String callRequestState) { this.callRequestState = callRequestState; }

}
