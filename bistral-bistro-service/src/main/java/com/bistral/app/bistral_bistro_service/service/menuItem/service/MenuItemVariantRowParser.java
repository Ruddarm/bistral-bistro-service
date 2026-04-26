package com.bistral.app.bistral_bistro_service.service.menuItem.service;

import com.bistral.app.bistral_bistro_service.contexts.ExcelErrorContext;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import com.bistral.app.bistral_bistro_service.entity.enums.ItemUnit;
import com.bistral.app.bistral_bistro_service.service.menuItem.interfaces.ExcelRowParser;
import com.bistral.app.bistral_bistro_service.utils.ExcelErrorLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

//@RequiredArgsConstructor
@Component
public class MenuItemVariantRowParser implements ExcelRowParser<MenuItemVariantEntity> {

    @Autowired
    private  ExcelErrorContext excelErrorContext;

    @Override
    public Optional<MenuItemVariantEntity> parse(List<String> row) {
        if (row.isEmpty() || row.get(0).equalsIgnoreCase("itemCode")) return Optional.empty();
        if (row.get(0) == null || row.get(0).isEmpty()) {
            log(row, "Item code is Empty");
            return Optional.empty();
        }
        if (row.get(1) == null || row.get(1).isBlank()) {
            log(row, "Variant Name is Empty");
            return Optional.empty();
        }
        if (row.get(2) == null || row.get(2).isBlank()) {
            log(row, "Price is Empty");
            return Optional.empty();

        }
        if (row.get(3) == null || row.get(3).isBlank()) {
            log(row, "Qty is Empty");
            return Optional.empty();
        }
        if (row.get(4) == null || row.get(4).isBlank()) {
            log(row, "Unit is Empty");
            return Optional.empty();
        }
        BigDecimal price;
        try {
            price = new BigDecimal(row.get(2));
        } catch (IllegalArgumentException ex) {
            log(row, "Invalid " +
                    "Price");
            return Optional.empty();

        }
        BigDecimal qty;
        try {
            qty = new BigDecimal(row.get(3));
        } catch (IllegalArgumentException ex) {
            log(row, "Invalid Qty");
            return Optional.empty();
        }
        ItemUnit unit;
        try {
            unit = ItemUnit.valueOf(row.get(4));
        } catch (IllegalArgumentException ex) {
            log(row, "Invalid Price");
            return Optional.empty();

        }
        return Optional.of(
                MenuItemVariantEntity
                        .builder()
                        .itemCode(row.get(0))
                        .variantName(row.get(1))
                        .price(price)
                        .qty(qty)
                        .unit(unit)
                        .build()
        );
    }

    private void log(List<String> values, String msg) {
        excelErrorContext.getLogger()
                .get()
                .log("itemVariant", values, msg);
    }
}
