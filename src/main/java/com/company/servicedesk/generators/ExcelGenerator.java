package com.company.servicedesk.generators;

import com.company.servicedesk.dtos.CallReportDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

//default language pt-BR
@Component
public class ExcelGenerator {
    private static final int FIRST_DATA_ROW = 2;
    private static final int[] COLUMN_WIDTHS = {
            4500, // Criado em
            5000, // Criado por
            5000, // Técnico
            4000, // Ativo
            5000, // Tipo de ativo
            5000, // Departamento
            12000, // Primeira análise
            12000, // Solução
            4500, // Terminado em
            3500, // Estado do chamado
            3500, // Urgência
            3500  // Impacto
    };
    private static final int COLUMN_COUNT = COLUMN_WIDTHS.length;

    public byte[] generateExcel(List<CallReportDTO> calls) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("chamados");

            sheet.createFreezePane(0, 2);
            //title merged column
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, COLUMN_COUNT - 1));

            for (int i = 0; i < COLUMN_COUNT; i++) {
                sheet.setColumnWidth(i, COLUMN_WIDTHS[i]);
            }

            //   ---STYLES---

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setWrapText(true);

            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setBorderLeft(BorderStyle.THICK);
            titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            titleStyle.setBorderBottom(BorderStyle.THICK);
            titleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            titleStyle.setBorderTop(BorderStyle.THICK);
            titleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            titleStyle.setBorderRight(BorderStyle.THICK);
            titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle dateStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            dateStyle.setDataFormat(dataFormat.getFormat("dd/MM/yyyy"));
            dateStyle.setBorderTop(BorderStyle.THIN);
            dateStyle.setBorderBottom(BorderStyle.THIN);
            dateStyle.setBorderLeft(BorderStyle.THIN);
            dateStyle.setBorderRight(BorderStyle.THIN);

            CellStyle lowStyle =
                    createStatusStyle(workbook, IndexedColors.LIGHT_GREEN);

            CellStyle mediumStyle =
                    createStatusStyle(workbook, IndexedColors.LIGHT_YELLOW);

            CellStyle highStyle =
                    createStatusStyle(workbook, IndexedColors.LIGHT_ORANGE);

            CellStyle criticalStyle =
                    createStatusStyle(workbook, IndexedColors.RED);

            CellStyle rowStyle = workbook.createCellStyle();
            rowStyle.setBorderTop(BorderStyle.THIN);
            rowStyle.setBorderBottom(BorderStyle.THIN);
            rowStyle.setBorderLeft(BorderStyle.THIN);
            rowStyle.setBorderRight(BorderStyle.THIN);
            rowStyle.setWrapText(true);

            XSSFFont headerFont = workbook.createFont();
            headerFont.setFontName("Arial");
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setBold(true);

            XSSFFont titleFont = workbook.createFont();
            titleFont.setFontName("Arial");
            titleFont.setFontHeightInPoints((short) 16);
            titleFont.setBold(true);

            headerStyle.setFont(headerFont);
            titleStyle.setFont(titleFont);

            //   --CREATE TABLE--

            Row title = sheet.createRow(0);

            Cell cellTitle = title.createCell(0);
            cellTitle.setCellValue("RELATÓRIO DE CHAMADOS");
            cellTitle.setCellStyle(titleStyle);

            Row header = sheet.createRow(1);

            header.createCell(0).setCellValue("Criado em");
            header.createCell(1).setCellValue("Criado por");
            header.createCell(2).setCellValue("Técnico");
            header.createCell(3).setCellValue("Ativo");
            header.createCell(4).setCellValue("Tipo de ativo");
            header.createCell(5).setCellValue("Departamento");
            header.createCell(6).setCellValue("Primeira análise");
            header.createCell(7).setCellValue("Solução");
            header.createCell(8).setCellValue("Terminado em");
            header.createCell(9).setCellValue("Estado");
            header.createCell(10).setCellValue("Urgência");
            header.createCell(11).setCellValue("Impacto");

            for (Cell cell : header) {
                cell.setCellStyle(headerStyle);
            }

            int rowNumber = FIRST_DATA_ROW;

            for (CallReportDTO call : calls) {
                createCallRow(sheet, rowNumber++, call, rowStyle, dateStyle,
                        lowStyle, mediumStyle, highStyle, criticalStyle);
            }

            sheet.setAutoFilter(new CellRangeAddress(1, sheet.getLastRowNum(), 0, COLUMN_COUNT - 1));

            workbook.write(output);

            return output.toByteArray();
        }
    }

    private void createCallRow(
            Sheet sheet,
            int rowNumber,
            CallReportDTO call,
            CellStyle rowStyle,
            CellStyle dateStyle,
            CellStyle lowStyle,
            CellStyle mediumStyle,
            CellStyle highStyle,
            CellStyle criticalStyle
    ) {
        Row row = sheet.createRow(rowNumber);
        row.setHeightInPoints(40);

        Cell createdAt = row.createCell(0);
        createdAt.setCellValue(call.beginDate());
        createdAt.setCellStyle(dateStyle);

        Cell createdBy = row.createCell(1);
        createdBy.setCellValue(call.createdBy());
        createdBy.setCellStyle(rowStyle);

        Cell assignedTo = row.createCell(2);
        assignedTo.setCellValue(call.assignedTo());
        assignedTo.setCellStyle(rowStyle);

        Cell asset = row.createCell(3);
        asset.setCellValue(call.asset().getAsset());
        asset.setCellStyle(rowStyle);

        Cell assetType = row.createCell(4);
        assetType.setCellValue(call.assetType().getAssetType());
        assetType.setCellStyle(rowStyle);

        Cell department = row.createCell(5);
        department.setCellValue(call.department().getDepartment());
        department.setCellStyle(rowStyle);

        Cell firstAnalysis = row.createCell(6);
        firstAnalysis.setCellValue(call.firstAnalysis());
        firstAnalysis.setCellStyle(rowStyle);

        Cell solution = row.createCell(7);
        if (call.solution() != null) {
            solution.setCellValue(call.solution());
        }
        solution.setCellStyle(rowStyle);

        Cell endDate = row.createCell(8);
        if (call.endDate() != null) {
            endDate.setCellValue(call.endDate());
        }
        endDate.setCellStyle(dateStyle);

        Cell callState = row.createCell(9);
        callState.setCellValue(call.callState().getCallState());
        callState.setCellStyle(rowStyle);

        Cell urgency = row.createCell(10);
        urgency.setCellValue(call.urgency().getUrgency());
        switch (call.urgency()) {
            case LOW -> urgency.setCellStyle(lowStyle);
            case MEDIUM -> urgency.setCellStyle(mediumStyle);
            case HIGH -> urgency.setCellStyle(highStyle);
            case CRITICAL -> urgency.setCellStyle(criticalStyle);
        }

        Cell impact = row.createCell(11);
        impact.setCellValue(call.impact().getImpact());
        switch (call.impact()) {
            case LOW -> impact.setCellStyle(lowStyle);
            case MEDIUM -> impact.setCellStyle(mediumStyle);
            case HIGH -> impact.setCellStyle(highStyle);
            case CRITICAL -> impact.setCellStyle(criticalStyle);
        }
    }

    private CellStyle createStatusStyle (
            Workbook workbook,
            IndexedColors color
    ) {
        CellStyle style = workbook.createCellStyle();

        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }
}
