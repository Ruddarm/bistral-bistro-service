package com.bistral.app.bistral_bistro_service.service.menuItem.service;


import com.bistral.app.bistral_bistro_service.contexts.ExcelErrorContext;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import com.bistral.app.bistral_bistro_service.service.menuItem.interfaces.BatchInserter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
@Service
public class MenuItemVariantBatchInserter implements BatchInserter<MenuItemVariantEntity> {

    private final JdbcTemplate jdbcTemplate;
    private final ExcelErrorContext excelErrorContext;

    @Override
    public void insertBatch(List<MenuItemVariantEntity> menuItemVariantEntities) {
        String sql = """
                insert into menu_item_variant(variant_id,variant_name,price,unit,menu_item_id,tax_rate,is_tax_included,qty)values(
                ?,?,?,?,?,?,?,?
                )
                """;
        try {
            jdbcTemplate.batchUpdate(sql
                    , menuItemVariantEntities,
                    2000,
                    (ps, variant) -> {
                        ps.setObject(1, variant.getVariantId());
                        ps.setObject(2, variant.getVariantName());
                        ps.setObject(3, variant.getPrice());
                        ps.setObject(4, variant.getUnit().name());
                        ps.setObject(5, variant.getMenuItem().getItemId());
                        ps.setObject(6, variant.getTaxRate());
                        ps.setObject(7, variant.isTaxIncluded());
                        ps.setObject(8, variant.getQty());
                    });
        } catch (Exception exception) {
            if (menuItemVariantEntities.size() == 1) {
                MenuItemVariantEntity menuItemVariantEntity = menuItemVariantEntities.getFirst();
                excelErrorContext
                        .getLogger()
                        .get()
                        .log(
                                "itemVariant",
                                List.of(
                                        menuItemVariantEntity.getItemCode(),
                                        menuItemVariantEntity.getVariantName(),
                                        menuItemVariantEntity.getPrice().toString(),
                                        menuItemVariantEntity.getQty().toString(),
                                        menuItemVariantEntity.getUnit().toString()
                                ),
                                exception.getMessage()
                        );
                return;
            }
            int mid = menuItemVariantEntities.size() / 2;
            insertBatch(menuItemVariantEntities.subList(0, mid));
            insertBatch(menuItemVariantEntities.subList(mid, menuItemVariantEntities.size()));
            boolean flag = false;

        }

    }
}


