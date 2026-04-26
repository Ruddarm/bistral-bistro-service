package com.bistral.app.bistral_bistro_service.service.menuItem.service;

import com.bistral.app.bistral_bistro_service.contexts.ExcelErrorContext;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.service.menuItem.interfaces.BatchInserter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemBatchInserter implements BatchInserter<MenuItemEntity> {

    private final JdbcTemplate jdbcTemplate;
    private final ExcelErrorContext excelErrorContext;

    //    @Transactional
    @Override
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void insertBatch(List<MenuItemEntity> batch) {
        System.out.println("code is here");
        String sql_querry = """
                insert into menu_item(item_id,item_name,
                menu_id,category_id,is_veg)
                values(?,?,?,?,?)
                """;
        try {
            jdbcTemplate.batchUpdate(
                    sql_querry,
                    batch,
                    batch.size(),
                    (ps, item) -> {
                        ps.setObject(1, item.getItemId());
                        ps.setObject(2, item.getItemName());
                        ps.setObject(3, item.getMenu().getMenuId());
                        ps.setObject(4, item.getMenuItemCategory().getCategoryId());
                        ps
                                .setObject(5, item.isVeg());
                    }
            );
        } catch (Exception exception) {
            if (batch.size() == 1) {
                MenuItemEntity menuItemEntity = batch.get(0);
                excelErrorContext.getLogger().get()
                        .log("MenuItem",
                                List.of(menuItemEntity.getItemCode()
                                        , menuItemEntity.getItemName(),
                                        menuItemEntity.getMenuItemCategory().getCategoryId().toString(),
                                        String.valueOf(menuItemEntity.isVeg())
                                ), exception.getLocalizedMessage());
                return;
            }
//            exception.printStackTrace();
            int mid = batch.size() / 2;
            insertBatch(batch.subList(0, mid));
            insertBatch(batch.subList(mid, batch.size()));
//            System.out.println(exception.getMessage());
        }
    }


}
