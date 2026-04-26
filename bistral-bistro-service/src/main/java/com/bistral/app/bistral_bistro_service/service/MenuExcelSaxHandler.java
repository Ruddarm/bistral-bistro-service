//package com.bistral.app.bistral_bistro_service.service;
//
//import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
//import com.bistral.app.bistral_bistro_service.entity.MenuItemCategoryEntity;
//import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
//import com.bistral.app.bistral_bistro_service.repository.MenuItemRepository;
//import com.bistral.app.bistral_bistro_service.service.menuItem.service.ExcelSaxHandler;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.model.SharedStringsTable;
//import org.apache.poi.xssf.usermodel.XSSFRichTextString;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Service;
//
//
//import java.util.*;
//
//public class MenuExcelSaxHandler extends ExcelSaxHandler {
//
//    //    private final MenuItemRepository menuItemRepository;
//    private final MenuItemService menuItemService;
//    private final List<MenuItemEntity> menuItemEntitiesBatch;
//    private MenuEntity menuEntity;
//    private final Map<String, UUID> itemMap;
//
//    public MenuExcelSaxHandler(MenuItemService menuItemService, MenuEntity menuEntity, SharedStringsTable sharedStringsTable) {
//        super(sharedStringsTable);
//        this.menuItemService = menuItemService;
//        this.menuEntity=menuEntity;
//        this.menuItemEntitiesBatch = new ArrayList<>();
//        this.itemMap = new HashMap<>();
//    }
//
//
//    @Override
//    public void processRow(List<String> cell) {
//        if (cell.isEmpty() || "itemCode".equalsIgnoreCase(cell.get(0))) return;
//        MenuItemCategoryEntity categoryEntity = MenuItemCategoryEntity.builder().categoryId(UUID.fromString(cell.get(2))).build();
//        MenuItemEntity menuItemEntity = MenuItemEntity.builder()
//                .itemId(UUID.randomUUID())
//                .itemCode(cell.get(0))
//                .itemName(cell.get(1))
//                .menuItemCategory(categoryEntity)
//                .menu(menuEntity)
//                .isVeg(Boolean.parseBoolean(cell.get(3))).build();
//        menuItemEntitiesBatch.add(menuItemEntity);
//        if (menuItemEntitiesBatch.size() >= 2000) {
//            menuItemService.batchInsert(menuItemEntitiesBatch);
//            addMapping(menuItemEntitiesBatch);
//            menuItemEntitiesBatch.clear();
//        }
//    }
//
//    public void flush() {
//        if (!menuItemEntitiesBatch.isEmpty()) {
//            menuItemService.batchInsert(menuItemEntitiesBatch);
//            addMapping(menuItemEntitiesBatch);
//            menuItemEntitiesBatch.clear();
//        }
//    }
//
//    public void addMapping(List<MenuItemEntity> menuItemEntities) {
//        menuItemEntities.forEach((menuItem) -> itemMap.put(menuItem.getItemCode(), menuItem.getItemId()));
//    }
//
//    public Map<String, UUID> getItemMapping() {
//        return itemMap;
//    }
//
//    @Override
//    public Sheet errorSheet() {
//        return null;
//    }
//
//}
