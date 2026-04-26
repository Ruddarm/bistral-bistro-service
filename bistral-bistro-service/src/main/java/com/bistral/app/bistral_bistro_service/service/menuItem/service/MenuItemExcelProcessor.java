package com.bistral.app.bistral_bistro_service.service.menuItem.service;

import com.bistral.app.bistral_bistro_service.contexts.ExcelErrorContext;
import com.bistral.app.bistral_bistro_service.contexts.ExcelImportContext;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.service.menuItem.interfaces.BatchInserter;
import com.bistral.app.bistral_bistro_service.service.menuItem.interfaces.ExcelRowConsumer;
import com.bistral.app.bistral_bistro_service.service.menuItem.interfaces.ExcelRowParser;
import com.bistral.app.bistral_bistro_service.utils.ExcelErrorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Scope("prototype")
public class MenuItemExcelProcessor implements ExcelRowConsumer {

    @Autowired
    private ExcelErrorContext excelErrorContext;
    private final List<MenuItemEntity> menuItemEntitiesBatch;
    private final ExcelRowParser<MenuItemEntity> menuItemEntityExcelRowParser;
    private final BatchInserter<MenuItemEntity> batchInserter;
    private final MenuEntity menuEntity;
    private final ExcelImportContext excelImportContext;
//    private final ExcelErrorContext excelErrorContext;


    public MenuItemExcelProcessor(
            ExcelRowParser<MenuItemEntity> menuItemEntityExcelRowParser,
            BatchInserter<MenuItemEntity> batchInserter,
            MenuEntity menuEntity,
            ExcelImportContext excelImportContext
//            ExcelErrorContext excelErrorContext
    ) {
        this.menuEntity = menuEntity;
        this.menuItemEntityExcelRowParser = menuItemEntityExcelRowParser;
        this.batchInserter = batchInserter;
        this.excelImportContext = excelImportContext;
//        this.excelErrorContext = excelErrorContext;
        menuItemEntitiesBatch = new ArrayList<>();
    }

    @Override
    public void accept(List<String> values) {
//        for (String val : values)
//            System.out.println(val);
        MenuItemEntity menuItemEntity = menuItemEntityExcelRowParser.parse(values).orElse(null);
        if (menuItemEntity != null) {
            if (excelImportContext.getItemId(menuItemEntity.getItemCode()) == null) {
                menuItemEntity.setMenu(menuEntity);
                menuItemEntity.setItemId(UUID.randomUUID());
                menuItemEntitiesBatch.add(menuItemEntity);
                excelImportContext.registerItem(menuItemEntity.getItemCode(), menuItemEntity.getItemId());
                if (menuItemEntitiesBatch.size() >= 2000) {
                    batchInserter.insertBatch(menuItemEntitiesBatch);
                    menuItemEntitiesBatch.clear();
                }
            } else {
                excelErrorContext.getLogger()
                        .get()
                        .log("menuItem",
                                List.of(menuItemEntity.getItemCode(),
                                        menuItemEntity.getItemName()
                                        , menuItemEntity.getMenuItemCategory().getCategoryId().toString(),
                                        "" + menuItemEntity.isVeg()), "Duplicate Item Code"
                        );
            }
        }
    }

    @Override
    public void flush() {
        System.out.println("flushing");
        if (!menuItemEntitiesBatch.isEmpty()) {
//            System.out.println("flushing menu item "+menuItemEntitiesBatch.size());
            batchInserter.insertBatch(menuItemEntitiesBatch);
            menuItemEntitiesBatch.clear();
        }
    }
}
