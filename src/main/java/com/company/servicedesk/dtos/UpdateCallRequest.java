package com.company.servicedesk.dtos;

import com.company.servicedesk.models.Departments;
import com.company.servicedesk.models.Impact;
import com.company.servicedesk.models.Urgency;

import java.time.LocalDateTime;

public record UpdateCallRequest(LocalDateTime beginDate,
                                Departments departments,
                                String firstAnalyses,
                                String solution,
                                Urgency urgency,
                                Impact impact) {
}
