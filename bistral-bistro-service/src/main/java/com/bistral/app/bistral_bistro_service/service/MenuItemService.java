package com.bistral.app.bistral_bistro_service.service;


import com.bistral.app.bistral_bistro_service.dtos.MenuItemRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemResponse;
import com.bistral.app.bistral_bistro_service.dtos.MenuRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final ModelMapper modelMapper;
    private final MenuService menuService;
    private final MenuItemRepository menuItemRepository;

    /*
        Create MenuItem for menu
        @param menuItemRequest
        @return menuItemResponse
     */
    public MenuItemResponse createMenuItem(MenuItemRequest menuItemRequest) {
        MenuEntity menuEntity = menuService.findByMenuIdAndBistro_BistroId(menuItemRequest.getMenuId(),
                menuItemRequest.getBistroId());
        MenuItemEntity menuItemEntity = modelMapper.map(menuItemRequest, MenuItemEntity.class);
        menuItemEntity.setMenu(menuEntity);
        menuItemEntity = menuItemRepository.save(menuItemEntity);
        return modelMapper.map(menuItemEntity, MenuItemResponse.class);
    }

    public MenuItemEntity getMenuItemEntityById(UUID menuItemId, UUID menuId) {
        return menuItemRepository
                .findByItemIdAndMenu_MenuId(menuItemId, menuId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem", "MenuItem not found with Id" + menuItemId));
    }

    public MenuItemResponse updateMenuItem(UUID menuItemId, UUID menuId, Map<String, Object> updates) {
        MenuItemEntity menuItemEntity = getMenuItemEntityById(menuItemId, menuId);
        Set<String> blockedUpdates = Set.of("itemVariantEntityList", "menu");
        MenuItemEntity finalMenuItemEntity = menuItemEntity;
        updates.forEach((key, value) -> {
            if (blockedUpdates.contains(key)) return;
            Field field = ReflectionUtils.findField(MenuItemEntity.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, finalMenuItemEntity, value);
        });
        menuItemEntity = menuItemRepository.save(menuItemEntity);
        return modelMapper.map(menuItemEntity, MenuItemResponse.class);
    }

}
