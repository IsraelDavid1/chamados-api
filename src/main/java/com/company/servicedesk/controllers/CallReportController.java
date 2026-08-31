package com.company.servicedesk.controllers;

import com.company.servicedesk.models.ReportFile;
import com.company.servicedesk.services.CallReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class CallReportController {
    private final CallReportService callReportService;

    @GetMapping("/CSV")
    public ResponseEntity<byte[]> generateCsv() throws IOException {
        ReportFile reportFile = callReportService.generateCsv();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + reportFile.filename()
                )
                .contentType(reportFile.contentType())
                .body(reportFile.content());
    }

    @GetMapping("/CSV-")
    public ResponseEntity<byte[]> generateMonthlyCsv() throws IOException {
        ReportFile reportFile = callReportService.generateMonthlyCsv();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + reportFile.filename()
                )
                .contentType(reportFile.contentType())
                .body(reportFile.content());
    }

    @GetMapping("/CSV")
    public ResponseEntity<byte[]> generateExcel() throws IOException {
        ReportFile reportFile = callReportService.generateExcel();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + reportFile.filename()
                )
                .contentType(reportFile.contentType())
                .body(reportFile.content());
    }

    @GetMapping("/CSV")
    public ResponseEntity<byte[]> generateMonthlyExcel() throws IOException {
        ReportFile reportFile = callReportService.generateMonthlyExcel();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + reportFile.filename()
                )
                .contentType(reportFile.contentType())
                .body(reportFile.content());
    }
}
