package com.company.servicedesk.generators;

import com.company.servicedesk.dtos.CallReportDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

//default language pt-BR
@Component
public class CsvGenerator {
    public byte[] generateCsv(List<CallReportDTO> calls) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            output.write(0xEF);
            output.write(0xBB);
            output.write(0xBF);

            CSVPrinter printer = new CSVPrinter(
                    new OutputStreamWriter(output, StandardCharsets.UTF_8),
                    CSVFormat.DEFAULT.builder()
                            .setHeader("Criado em", "Criador", "Técnico",
                                    "Ativo", "Tipo do ativo", "Departamento", "Primeira análise",
                                    "Solução", "Terminada em", "Estado", "Urgência", "Impacto").get()
            );

            for (CallReportDTO call : calls) {
                printer.printRecord(
                        call.beginDate(),
                        call.createdBy(),
                        call.assignedTo(),
                        call.asset(),
                        call.assetType(),
                        call.department(),
                        call.firstAnalysis(),
                        call.solution(),
                        call.endDate(),
                        call.callState(),
                        call.urgency(),
                        call.impact()
                );
            }

            printer.flush();

            return output.toByteArray();
        }
    }
}
