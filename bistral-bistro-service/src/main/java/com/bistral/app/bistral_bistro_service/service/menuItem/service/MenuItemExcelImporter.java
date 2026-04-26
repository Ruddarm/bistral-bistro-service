package com.bistral.app.bistral_bistro_service.service.menuItem.service;

import com.bistral.app.bistral_bistro_service.contexts.ExcelErrorContext;
import com.bistral.app.bistral_bistro_service.contexts.ExcelImportContext;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import com.bistral.app.bistral_bistro_service.service.MenuService;
import com.bistral.app.bistral_bistro_service.service.menuItem.interfaces.ExcelRowConsumer;
import com.bistral.app.bistral_bistro_service.service.menuItem.interfaces.IMenuItemExcelImporter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MenuItemExcelImporter implements IMenuItemExcelImporter {

    private final MenuItemVariantBatchInserter menuItemVariantBatchInserter;
    private final MenuItemBatchInserter menuItemBatchInserter;
    private final MenuService menuService;
    private final ApplicationContext applicationContext;
    private final MenuItemRowParser menuItemRowParser;
    private final MenuItemVariantRowParser menuItemVariantRowParser;

    private final ExcelErrorContext excelErrorContext;

    @Override
    public Workbook importItem(UUID menuId, MultipartFile multipartFile) {
        try (InputStream is = multipartFile.getInputStream()) {
            OPCPackage opcPackage = OPCPackage.open(is);
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            SharedStringsTable sharedStringsTable = (SharedStringsTable) xssfReader.getSharedStringsTable();
            MenuEntity menuEntity = menuService.findById(menuId);
            ExcelImportContext parentContext = applicationContext.getBean(ExcelImportContext.class);
            ExcelRowConsumer menuItemRowConsumer = applicationContext.getBean(MenuItemExcelProcessor.class, menuItemRowParser, menuItemBatchInserter, menuEntity, parentContext);
            ExcelSaxHandler menuItemHandler = applicationContext.getBean(ExcelSaxHandler.class, menuItemRowConsumer, sharedStringsTable);
            XMLReader parser = XMLHelper.newXMLReader();
            parser.setContentHandler(menuItemHandler);
            Iterator<InputStream> inputStreamIterator = xssfReader.getSheetsData();
            parser.parse(new InputSource(inputStreamIterator.next()));
            parser.setEntityResolver((publicId, systemId) -> new InputSource(new StringReader("")));
            menuItemRowConsumer.flush();
            ExcelRowConsumer menuItemVariantConsumer = applicationContext.getBean(MenuItemVariantExcelProcessor.class, parentContext, menuItemVariantRowParser, menuItemVariantBatchInserter);
            ExcelSaxHandler menuItemVariantHandler = applicationContext.getBean(ExcelSaxHandler.class, menuItemVariantConsumer, sharedStringsTable);
            parser.setContentHandler(menuItemVariantHandler);
            parser.parse(new InputSource(inputStreamIterator.next()));
            menuItemVariantConsumer.flush();
        } catch (Exception e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return excelErrorContext.getLogger().get().getWorkbook();
    }




}
