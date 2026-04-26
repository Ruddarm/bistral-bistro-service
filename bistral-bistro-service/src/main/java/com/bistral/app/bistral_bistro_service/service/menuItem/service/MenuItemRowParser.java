package com.bistral.app.bistral_bistro_service.service.menuItem.service;

import com.bistral.app.bistral_bistro_service.contexts.ExcelErrorContext;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemCategoryEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.service.menuItem.interfaces.ExcelRowParser;
import com.bistral.app.bistral_bistro_service.utils.ExcelErrorLogger;
import com.thoughtworks.xstream.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.RecursiveTask;


@Slf4j
//@RequiredArgsConstructor
@Component
public class MenuItemRowParser implements ExcelRowParser<MenuItemEntity> {


    @Autowired
    private  ExcelErrorContext excelErrorContext;

    @Override
    public Optional<MenuItemEntity> parse(List<String> row) {

        if (row.isEmpty() || row.get(0).equalsIgnoreCase("ItemCode"))
            return Optional.empty();

        if (row.size() < 4) {
            log(row, "Incomplete fields");
            return Optional.empty();
        }

        if (row.get(0).isBlank() || !row.get(0).startsWith("ITEM")) {
            log(row, "Invalid Item Code");
            return Optional.empty();
        }

        if (row.get(1).isBlank()) {
            log(row, "Item Name is Empty");
            return Optional.empty();
        }

        if (row.get(3) == null || !row.get(3).equalsIgnoreCase("true") && !row.get(3).equalsIgnoreCase("false") && !row.get(3).equals("1")) {
            log(row, "Invalid isVeg value");
            return Optional.empty();
        }
        UUID categoryId;
        try {
            categoryId = UUID.fromString(row.get(2));
        } catch (Exception e) {
            log(row, "Invalid Category UUID");
            return Optional.empty();
        }
        return Optional.of(
                MenuItemEntity.builder()
                        .itemCode(row.get(0))
                        .itemName(row.get(1))
                        .menuItemCategory(MenuItemCategoryEntity.builder().categoryId(categoryId).build())
                        .isVeg(Boolean.parseBoolean(row.get(3)))
                        .build()
        );
    }

    private void log(List<String> row, String msg) {
        System.out.println(msg);
        excelErrorContext.getLogger().get().log("MenuItem", row, msg);
    }

}
