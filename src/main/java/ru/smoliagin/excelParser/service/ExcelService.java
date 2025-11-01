package ru.smoliagin.excelParser.service;

import org.apache.coyote.BadRequestException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    public double findNthMinimum(String filePath, int n) throws IOException {
        if (n <= 0) {
            throw new BadRequestException("N должно быть положительным числом");
        }

        List<Double> numbers = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {
            var sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    double value = cell.getNumericCellValue();
                    numbers.add(value);
                }
            }
        }

        return findNMin(numbers, 0, numbers.size()- 1, n);
    }

    private double findNMin(List<Double> list, int low, int high, int n) throws BadRequestException {
        if (list.size() < n) {
            throw new BadRequestException("В файле меньше чем " + n + " чисел");
        }
        if (low < high) {
            int pi = partition(list, low, high);
            findNMin(list, low, pi - 1, n);
            findNMin(list, pi + 1, high, n);
        }
        return list.get(n-1);
    }

    private int partition(List<Double> list, int low, int high) {
        Double pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j) <= pivot) {
                i++;
                Double temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        Double temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }
}