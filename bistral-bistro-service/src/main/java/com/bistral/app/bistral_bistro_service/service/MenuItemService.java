package com.bistral.app.bistral_bistro_service.service;


import com.bistral.app.bistral_bistro_service.dtos.MenuItemRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemResponse;
import com.bistral.app.bistral_bistro_service.dtos.MenuRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemCategoryEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.mapperInterface.MenuItemMapper;
import com.bistral.app.bistral_bistro_service.repository.MenuItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final ModelMapper modelMapper;
    private final MenuService menuService;
    private final MenuItemCategoryService menuItemCategoryService;
    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    /*
        Create MenuItem for menu
        @param menuItemRequest
        @return menuItemResponse
     */
    @Transactional
    public MenuItemResponse createMenuItem(MenuItemRequest menuItemRequest) {
        MenuEntity menuEntity = menuService.findByMenuIdAndBistro_BistroId(menuItemRequest.getMenuId(),
                menuItemRequest.getBistroId());
        MenuItemCategoryEntity menuItemCategoryEntity = menuItemCategoryService.findById(menuItemRequest.getCategoryId());
        MenuItemEntity menuItemEntity = menuItemMapper.toMenuItemEntity(menuItemRequest);
        MenuItemEntity finalMenuItemEntity = menuItemEntity;
        menuItemEntity.getItemVariantEntityList().forEach(menuItemVariantEntity -> {
            menuItemVariantEntity.setMenuItem(finalMenuItemEntity);
        });
        menuItemEntity.setMenu(menuEntity);
        menuItemEntity.setMenuItemCategory(menuItemCategoryEntity);
        menuItemEntity = menuItemRepository.save(menuItemEntity);
        return menuItemMapper.toMenuItemResponse(menuItemEntity);
    }


    public MenuItemEntity getMenuItemEntityById(UUID menuId, UUID menuItemId) {
        return menuItemRepository
                .findByItemIdAndMenu_MenuId(menuItemId, menuId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem", "MenuItem not found with Id" + menuItemId));
    }

    public Page<MenuItemEntity> serachMenuItemsByMenu(String keyword, UUID menuId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("itemName").ascending());
        return menuItemRepository.searchMenuItemsByMenuWithVariants(menuId, keyword, pageable);
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
