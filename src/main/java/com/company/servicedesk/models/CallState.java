package com.company.servicedesk.models;

import lombok.Getter;

@Getter
public enum CallState {
    INCOMPLETE("incomplete"),
    COMPLETE("complete");

    private final String callState;

    CallState(String callState) {
        this.callState = callState;
    }
}
