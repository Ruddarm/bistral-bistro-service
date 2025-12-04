package com.bistral.app.bistral_bistro_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MenuCardResponse {
    private UUID menuId;
    private String menuName;
    HashMap <String, List<MenuItemResponse>> menuItems;
}
