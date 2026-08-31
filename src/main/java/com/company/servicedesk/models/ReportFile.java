package com.company.servicedesk.models;

import org.springframework.http.MediaType;

public record ReportFile(byte[] content,
                         String filename,
                         MediaType contentType) {
}
