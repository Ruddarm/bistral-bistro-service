//package com.bistral.app.bistral_bistro_service.service;
//
//import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
//import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
//import com.bistral.app.bistral_bistro_service.entity.enums.ItemUnit;
//import com.bistral.app.bistral_bistro_service.service.menuItem.service.ExcelSaxHandler;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.Value;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.model.SharedStringsTable;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//public class MenuItemVariantExcelSaxHandler extends ExcelSaxHandler {
//
//    private final List<MenuItemVariantEntity> menuItemVariantEntities;
//    private final MenuItemVariantService menuItemVariantService;
//    private final Map<String, UUID> itemMap;
//
//    public MenuItemVariantExcelSaxHandler(Map<String, UUID> itemMap, SharedStringsTable sharedStringsTable, MenuItemVariantService menuItemVariantService) {
//        super(sharedStringsTable);
//        this.menuItemVariantService = menuItemVariantService;
//        this.menuItemVariantEntities = new ArrayList<>();
//        this.itemMap = itemMap;
//    }
//
//
//    public void processRow(List<String> rowValues) {
//        if (rowValues.isEmpty() || rowValues.get(0).equals("ItemCode")) return;
//        UUID menuItemId = itemMap.getOrDefault(rowValues.get(0), null);
//        if (menuItemId != null) {
//            MenuItemEntity menuItemEntity = MenuItemEntity.builder().itemId(menuItemId).build();
//            MenuItemVariantEntity menuItemVariantEntity =
//                    MenuItemVariantEntity.builder()
//                            .variantId(UUID.randomUUID())
//                            .variantName(rowValues.get(1))
//                            .price(BigDecimal.valueOf(Long.parseLong(rowValues.get(2))))
//                            .qty(BigDecimal.valueOf(Long.parseLong(rowValues.get(3))))
//                            .unit(ItemUnit.valueOf(rowValues.get(4)))
//                            .menuItem(menuItemEntity)
//                            .build();
//            menuItemVariantEntities.add(menuItemVariantEntity);
//            if (menuItemVariantEntities.size() >= 2000) {
//                menuItemVariantService.insertBatch(menuItemVariantEntities);
//                menuItemVariantEntities.clear();
//            }
//        } else {
//            //handel error that parent not found
//            System.out.println("parent Not found");
//        }
//
//    }
//
//
//}
