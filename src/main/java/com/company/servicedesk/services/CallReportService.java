package com.company.servicedesk.services;

import com.company.servicedesk.dtos.CallReportDTO;
import com.company.servicedesk.generators.CsvGenerator;
import com.company.servicedesk.generators.ExcelGenerator;
import com.company.servicedesk.models.ReportFile;
import com.company.servicedesk.repositories.CallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CallReportService {
    private final CallRepository callRepository;
    private final CsvGenerator csvGenerator;
    private final ExcelGenerator excelGenerator;

    public ReportFile generateCsv() throws IOException {
        List<CallReportDTO> calls = callRepository.findCallsForReport();

        return new ReportFile(csvGenerator.generateCsv(calls),
                "chamados.csv",
                MediaType.parseMediaType("text/csv"));
    }

    public ReportFile generateMonthlyCsv() throws IOException {
        LocalDateTime firstDayOfTheMonth = getFirstDayOfTheMonth();
        LocalDateTime lastDayOfTheMonth = getLastDayOfTheMonth();

        List<CallReportDTO> calls = callRepository.findMonthlyCallsForReport(
                firstDayOfTheMonth,
                lastDayOfTheMonth);

        return new ReportFile(csvGenerator.generateCsv(calls),
                "chamados.csv",
                MediaType.parseMediaType("text/csv"));
    }

    public ReportFile generateExcel() throws IOException {
        List<CallReportDTO> calls = callRepository.findCallsForReport();

        return new ReportFile(excelGenerator.generateExcel(calls),
                "chamados.xlsx",
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }

    public ReportFile generateMonthlyExcel() throws IOException {
        LocalDateTime firstDayOfTheMonth = getFirstDayOfTheMonth();
        LocalDateTime lastDayOfTheMonth = getLastDayOfTheMonth();

        List<CallReportDTO> calls = callRepository.findMonthlyCallsForReport(
                firstDayOfTheMonth,
                lastDayOfTheMonth);

        return new ReportFile(excelGenerator.generateExcel(calls),
                "chamados.xlsx",
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }

    private LocalDateTime getFirstDayOfTheMonth() {
        LocalDateTime thisMonth = LocalDateTime.now();
        return thisMonth
                .with(TemporalAdjusters.firstDayOfMonth())
                .toLocalDate()
                .atStartOfDay();
    }

    private LocalDateTime getLastDayOfTheMonth() {
        LocalDateTime thisMonth = LocalDateTime.now();
        return thisMonth
                .with(TemporalAdjusters.lastDayOfMonth())
                .toLocalDate()
                .atTime(23, 59, 59);
    }
}
