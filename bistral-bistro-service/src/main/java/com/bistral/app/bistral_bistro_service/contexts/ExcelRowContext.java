package com.bistral.app.bistral_bistro_service.contexts;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ExcelRowContext {
    private boolean isCellValue;
    private boolean isCellTypeString;
    private String currentCellRef;
    private List<String> rowValues;
    private int colIdx;
    private StringBuilder currentValue;

    public  ExcelRowContext(){
        isCellValue=false;
        isCellTypeString=false;
        rowValues = new ArrayList<>();
        currentValue = new StringBuilder();
    }
}
