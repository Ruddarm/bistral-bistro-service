package com.bistral.app.bistral_bistro_service.service.menuItem.service;

import com.bistral.app.bistral_bistro_service.contexts.ExcelRowContext;
import com.bistral.app.bistral_bistro_service.service.menuItem.interfaces.ExcelRowConsumer;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


@Component
@Scope("prototype")
public class ExcelSaxHandler extends DefaultHandler {

    private final ExcelRowContext excelRowContext;
    private final ExcelRowConsumer excelRowConsumer;
    private final SharedStringsTable sharedStringsTable;

    public ExcelSaxHandler(ExcelRowConsumer excelRowConsumer, SharedStringsTable sharedStringsTable) {
        this.excelRowContext = new ExcelRowContext();
        this.excelRowConsumer = excelRowConsumer;
        this.sharedStringsTable = sharedStringsTable;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("c".equals(qName)) {
            String type = attributes.getValue("t");
            String cellRef = attributes.getValue("r");
            excelRowContext.setCellTypeString("s".equals(type));
            excelRowContext.setCurrentCellRef(cellRef);
            int colIdx = getColIdx(excelRowContext.getCurrentCellRef());
            excelRowContext.setColIdx(colIdx);
            while (excelRowContext.getRowValues().size() <= colIdx)
                excelRowContext.getRowValues().add("");

        }
        if ("v".equals(qName)) {
            excelRowContext.setCellValue(true);
            excelRowContext.getCurrentValue().setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (excelRowContext.isCellValue()) excelRowContext.getCurrentValue().append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("v".equals(qName)) {
            String value;
            if (excelRowContext.isCellTypeString()) {
                int idx = Integer.parseInt(excelRowContext.getCurrentValue().toString());
                value = new XSSFRichTextString(sharedStringsTable.getItemAt(idx).toString()).getString();
            } else {
                value = excelRowContext.getCurrentValue().toString();
            }
            excelRowContext.getRowValues().set(excelRowContext.getColIdx(), value);
            excelRowContext.setCellValue(false);
        }
        if ("row".equals(qName)) {
            this.excelRowConsumer.accept(excelRowContext.getRowValues());
            excelRowContext.getRowValues().clear();
        }
    }

    public int getColIdx(String ref) {
        return new CellReference(ref).getCol();
    }


}
