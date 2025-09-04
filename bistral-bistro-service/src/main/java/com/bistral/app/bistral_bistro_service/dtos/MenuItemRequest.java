package com.bistral.app.bistral_bistro_service.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MenuItemRequest {
    @NotEmpty
    private String itemName;
    @NotNull
    private  double price;
    @NotNull
    private  double taxRate;
    @NotNull
    private  boolean isTaxIncluded;
    @NotNull
    private boolean isVeg;
    @NotNull
    private UUID menuId;

}
