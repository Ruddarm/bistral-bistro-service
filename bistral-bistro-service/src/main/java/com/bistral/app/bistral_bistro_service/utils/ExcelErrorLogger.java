package com.bistral.app.bistral_bistro_service.utils;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ExcelErrorLogger {

    private final SXSSFWorkbook workbook;
    private final Map<String, Sheet> sheets = new HashMap<>();
    private final Map<String, Integer> rowIndexes = new HashMap<>();

    public ExcelErrorLogger() {
        workbook = new SXSSFWorkbook(100); // keep 100 rows in memory
    }

    public void log(String sheetName, List<String> rowData, String message) {
        Sheet sheet = sheets.computeIfAbsent(sheetName.toLowerCase(), name -> workbook.createSheet(name.toLowerCase()));
        int rowIndex = rowIndexes.getOrDefault(sheetName.toLowerCase(), 0);
        Row row = sheet.createRow(rowIndex);
//        row.createCell(0).setCellValue(rowNumber);
        for (int i = 0; i < rowData.size(); i++) {
            row.createCell(i).setCellValue(rowData.get(i));
        }
        row.createCell(rowData.size()).setCellValue(message);
        rowIndexes.put(sheetName.toLowerCase(), rowIndex+1);
    }

}
