//package com.bistral.app.bistral_bistro_service.service;
//
//import com.bistral.app.bistral_bistro_service.dtos.MenuItemExcelDto;
//import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
//import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
//import com.bistral.app.bistral_bistro_service.utils.ExcelBuilder;
//import lombok.RequiredArgsConstructor;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.util.XMLHelper;
//import org.apache.poi.xssf.eventusermodel.XSSFReader;
//import org.apache.poi.xssf.model.SharedStringsTable;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.xml.sax.InputSource;
//import org.xml.sax.XMLReader;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.*;
//import java.util.function.Function;
//
//
//@Service
//@RequiredArgsConstructor
//public class MenuItemAndVariantExcelImport {
//
//    private final MenuItemVariantService menuItemVariantService;
//    private final MenuItemService menuItemService;
//    private final MenuService menuService;
////    private final MenuExcelSaxHandler menuExcelSaxHandler;
////    private final MenuItemVariantExcelSaxHandler menuItemVariantExcelSaxHandler;
//
//    public Workbook importMenuExcel(UUID menuId, MultipartFile multipartFile) throws IOException {
////        HashMap<String, UUID> itemMap = new HashMap<>();
//        SXSSFWorkbook errorBook = new SXSSFWorkbook();
//        Sheet itemError = errorBook.createSheet("itemError");
//        ExcelBuilder<MenuItemEntity> excelBuilder = new ExcelBuilder<>(itemError);
//        MenuEntity menuEntity = menuService.findById(menuId);
//        try (InputStream is = multipartFile.getInputStream()) {
//            //saving menuItems;
//            OPCPackage opcPackage = OPCPackage.open(is);
//            XSSFReader xssfReader = new XSSFReader(opcPackage);
//            SharedStringsTable sharedStringsTable = (SharedStringsTable) xssfReader.getSharedStringsTable();
//            MenuExcelSaxHandler menuExcelSaxHandler = new MenuExcelSaxHandler(menuItemService, menuEntity, sharedStringsTable);
//            XMLReader parser = XMLHelper.newXMLReader();
//            parser.setContentHandler(menuExcelSaxHandler);
//            java.util.Iterator<InputStream> iterator = xssfReader.getSheetsData();
//            parser.parse(new InputSource(iterator.next()));
//            menuExcelSaxHandler.flush();
//            MenuItemVariantExcelSaxHandler menuItemVariantExcelSaxHandler = new MenuItemVariantExcelSaxHandler(menuExcelSaxHandler.getItemMapping(), sharedStringsTable, menuItemVariantService);
//            XMLReader variantParser = XMLHelper.newXMLReader();
//            variantParser.setContentHandler(menuItemVariantExcelSaxHandler);
//            InputStream variantSheet = iterator.next();
//            variantParser.parse(new InputSource(variantSheet));
//            menuItemVariantExcelSaxHandler.flush();
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//        return null;
//    }
//}
