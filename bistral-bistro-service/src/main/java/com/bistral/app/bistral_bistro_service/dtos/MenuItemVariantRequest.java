package com.bistral.app.bistral_bistro_service.dtos;

import com.bistral.app.bistral_bistro_service.enums.ItemUnit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemVariantRequest {

    @NotNull
    @Min(0)
    private BigDecimal price;
    @NotNull
    @NotEmpty
    private  String variantName;
//    @NotNull
    @Min(0)
    private BigDecimal taxRate;
//    @NotNull
    private boolean isTaxIncluded=false;
    @NotNull
    @Min(0)
    private BigDecimal qty;
    @NotNull
    private ItemUnit unit;
}
