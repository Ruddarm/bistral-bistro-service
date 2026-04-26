package com.bistral.app.bistral_bistro_service.dtos;

import lombok.*;

import java.util.UUID;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemExcelDto {

    private String itemCode;
    private UUID itemId;
    private String itemName;
    private UUID categoryId;
    private boolean isVeg;
    private String errorMsg;
}
