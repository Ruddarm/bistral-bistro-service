package com.bistral.app.bistral_bistro_service.dtos;

import com.bistral.app.bistral_bistro_service.entity.enums.ItemUnit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class MenuItemRequest {
    @NotEmpty
    private String itemName;
    @NotNull
    private boolean isVeg;
    @NotNull
    private UUID menuId;
    @NotNull
    private  UUID bistroId;
    @NotNull
    private  UUID categoryId;
    private List<MenuItemVariantRequest> menuItemVariantRequests = new ArrayList<>();

}
