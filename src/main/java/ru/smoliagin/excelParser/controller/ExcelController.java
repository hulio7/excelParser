package ru.smoliagin.excelParser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import ru.smoliagin.excelParser.controller.model.ObjectForRequest;
import ru.smoliagin.excelParser.service.ExcelService;

@RestController
@RequestMapping("/api/excel")
@Tag(name = "Excel Parser API")
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;

    @SneakyThrows
    @GetMapping("/find-nth-min")
    @Operation(description = "Найти N-ное минимальное число из Excel файла")
    public String findNthMinimum(ObjectForRequest request) {
        try {
            double result = excelService.findNthMinimum(request.getFilePath(), request.getN());
            return String.format("N-ное минимальное равняется = %.2f", result);
        } catch (Exception e) {
            throw e;
        }
    }
}
