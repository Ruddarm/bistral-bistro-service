package com.bistral.app.bistral_bistro_service.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ExcelBuilder<T> {
    private final Sheet sheet;
    private int rowIdx = 0;

    public ExcelBuilder(Sheet sheet) {
        this.sheet = sheet;
    }

    public void writeHeader(List<String> columns, CellStyle cellStyle) {

        Row row = sheet.createRow(rowIdx++);
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns.get(i));
            cell.setCellStyle(cellStyle);
        }
        ;
    }
    public void writeHeader(List<String> columns) {
        Row row = sheet.createRow(rowIdx++);
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns.get(i));
        }
        ;
    }

    public void writeRow(T data, List<String> selectedColumn, Map<String, Function<T, Object>> extractor) {
        Row row = sheet.createRow(rowIdx++);
        for (int i = 0; i < selectedColumn.size(); i++) {
            Cell cell = row.createCell(i);
            Function<T, Object> fun = extractor.get(selectedColumn.get(i));
            if (fun != null) {
                Object value = fun.apply(data);
                if (value instanceof Number) {
                    cell.setCellValue(((Number) value).doubleValue());
                } else if (value instanceof Boolean) {
                    cell.setCellValue((Boolean) value);
                } else {
                    cell.setCellValue(value == null ? "" : value.toString());
                }
            }
        }
    }

    public void writeRow(List<Object> data){
        Row row = sheet.createRow(rowIdx++);
        for(int i = 0 ; i < data.size();i++){
            Object d = data.get(i);
            Cell cell = row.createCell(i);
            if(d instanceof  Number){
                cell.setCellValue(((Number)d).doubleValue());
            }else  if(d instanceof  Boolean){
                cell.setCellValue(((Boolean) d).booleanValue());
            }else {
                cell.setCellValue(d==null?"":d.toString());
            }
        }
    }

    public void autoSizeColumn(int col) {
        for (int i = 0; i < col; i++) {
//            sheet.trackAllColumnsForAutoSizing();
            sheet.autoSizeColumn(i);
        }
    }

}
