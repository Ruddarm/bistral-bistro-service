package com.bistral.app.bistral_bistro_service.dtos;

import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponse {
    private UUID itemId;
    private String itemName;
    private boolean isVeg;
    private UUID categoryId;
    private String categoryName;
    private List<MenuItemVariantResponse> menuItemVariantResponsesList = new ArrayList<>();
}
