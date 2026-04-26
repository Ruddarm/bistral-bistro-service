package com.bistral.app.bistral_bistro_service.service.menuItem.service;

import com.bistral.app.bistral_bistro_service.contexts.ExcelErrorContext;
import com.bistral.app.bistral_bistro_service.contexts.ExcelImportContext;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import com.bistral.app.bistral_bistro_service.service.menuItem.interfaces.ExcelRowConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//@Service
//@RequiredArgsConstructor
@Component
@Scope("prototype")
public class MenuItemVariantExcelProcessor implements ExcelRowConsumer {

    @Autowired
    private ExcelErrorContext excelErrorContext;
    private final ExcelImportContext excelImportContext;
    private final MenuItemVariantRowParser menuItemVariantRowParser;
    private final MenuItemVariantBatchInserter batchInserter;
    private final List<MenuItemVariantEntity> menuItemVariantEntitiesBatch;

    public MenuItemVariantExcelProcessor(ExcelImportContext excelImportContext, MenuItemVariantRowParser menuItemVariantRowParser
            ,
                                         MenuItemVariantBatchInserter batchInserter) {
        this.excelImportContext = excelImportContext;
        this.menuItemVariantRowParser = menuItemVariantRowParser;
        this.batchInserter = batchInserter;
        menuItemVariantEntitiesBatch = new ArrayList<>();
    }

    @Override
    public void accept(List<String> values) {
        MenuItemVariantEntity menuItemVariantEntity =
                menuItemVariantRowParser.parse(values).orElse(null);
        if(menuItemVariantEntity==null) return;
        if (excelImportContext.getItemId(menuItemVariantEntity.getItemCode()) != null) {
            menuItemVariantEntity.setMenuItem(MenuItemEntity.builder().itemId(excelImportContext.getItemId(menuItemVariantEntity.getItemCode())).build());
            menuItemVariantEntity.setVariantId(UUID.randomUUID());
            menuItemVariantEntitiesBatch.add(menuItemVariantEntity);
            if (menuItemVariantEntitiesBatch.size() >= 2000) {
                this.flush();
            }
        } else {
            excelErrorContext.getLogger()
                    .get()
                    .log("itemVariant",
                            List.of(menuItemVariantEntity.
                                            getItemCode(),
                                    menuItemVariantEntity.getVariantName(),
                                    menuItemVariantEntity.getPrice().toString(),
                                    menuItemVariantEntity.getQty().toString(),
                                    menuItemVariantEntity.getUnit().toString()), "Menu Item Not Found " + menuItemVariantEntity.getItemCode());
        }

    }

    @Override
    public void flush() {
        if (!menuItemVariantEntitiesBatch.isEmpty()) {
            batchInserter.insertBatch(menuItemVariantEntitiesBatch);
            menuItemVariantEntitiesBatch.clear();
        }
    }
}
