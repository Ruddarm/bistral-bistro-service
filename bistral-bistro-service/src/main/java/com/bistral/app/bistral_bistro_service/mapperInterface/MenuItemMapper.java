package com.bistral.app.bistral_bistro_service.mapperInterface;


import com.bistral.app.bistral_bistro_service.contexts.UserContextHolder;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {MenuItemVariantMapper.class})
public interface MenuItemMapper {

    default MenuItemEntity toMenuItemEntity(MenuItemRequest menuItemRequest) {
        return MenuItemEntity.builder()
                .itemName(menuItemRequest.getItemName())
                .isVeg(menuItemRequest.isVeg())
                .itemVariantEntityList(menuItemRequest.getMenuItemVariantRequests().stream()
                        .map(menuItemVariantRequest ->
                                MenuItemVariantEntity.builder()
                                        .variantName(menuItemVariantRequest.getVariantName())
                                        .price(menuItemVariantRequest.getPrice())
                                        .qty(menuItemVariantRequest.getQty())
                                        .unit(menuItemVariantRequest.getUnit())
                                        .createdBy(UserContextHolder.getAuthContext().getUserId())
                                        .build()
                        ).collect(Collectors.toList()))
                .build();
    }

    MenuItemResponse toMenuItemResponse(MenuItemEntity menuItemEntity);

}
