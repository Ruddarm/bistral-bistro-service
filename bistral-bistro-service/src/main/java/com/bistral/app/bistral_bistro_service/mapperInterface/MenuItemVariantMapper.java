package com.bistral.app.bistral_bistro_service.mapperInterface;


import com.bistral.app.bistral_bistro_service.dtos.MenuItemRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuItemVariantMapper {
    MenuItemVariantEntity toVariantEntity(MenuItemVariantRequest menuItemVariantRequest);

    MenuItemVariantResponse toVariantResponse(MenuItemVariantEntity menuItemVariantEntity);
}
