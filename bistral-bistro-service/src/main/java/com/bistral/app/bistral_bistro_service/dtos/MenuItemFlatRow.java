package com.bistral.app.bistral_bistro_service.dtos;

import com.bistral.app.bistral_bistro_service.enums.ItemUnit;

import java.math.BigDecimal;
import java.util.UUID;

public interface MenuItemFlatRow {
    UUID getItemId();
    String getItemName();
    Boolean getIsVeg();
    String getVariantName();
    UUID getCategoryId();
    String getCategoryName();
    UUID getVariantId();
    BigDecimal getPrice();
    Boolean getIsTaxIncluded();
    BigDecimal getQty();
    ItemUnit getItemUnit();

}
