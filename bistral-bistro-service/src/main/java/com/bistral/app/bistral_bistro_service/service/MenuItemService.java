package com.bistral.app.bistral_bistro_service.service;


import com.bistral.app.bistral_bistro_service.dtos.*;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemCategoryEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.mapperInterface.MenuItemMapper;
import com.bistral.app.bistral_bistro_service.repository.MenuItemRepository;
import com.bistral.app.bistral_bistro_service.service.notification.interfaces.INotificationBroker;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.Row;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final ModelMapper modelMapper;
    private final MenuService menuService;
    private final MenuItemCategoryService menuItemCategoryService;
    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    private  final INotificationBroker notificationBroker;
    private final JdbcTemplate jdbcTemplate;
//    private final MenuExcelSaxHandler menuExcelSaxHandler;

    //    private final MenuItemVariantService menuItemVariantService;
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
//        notificationBroker.saveAndNotify(TextNotification.builder().build());
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


    @Transactional
    public List<MenuItemEntity> saveAll(List<MenuItemEntity> menuItemEntities) {
        return menuItemEntities = menuItemRepository.saveAll(menuItemEntities);
    }




//    public List

    public MenuItemEntity getMenuItemEntityFromRow(Row row) {
        MenuItemCategoryEntity menuItemCategory = menuItemCategoryService.findById(UUID.fromString(row.getCell(2).getStringCellValue()));
        return MenuItemEntity
                .builder()
                .itemCode(row.getCell(0).getStringCellValue())
                .itemName(row.getCell(1).getStringCellValue())
                .menuItemCategory(menuItemCategory)
                .isVeg(row.getCell(3).getBooleanCellValue())
                .build();
    }

    public Map<String, Function<MenuItemExcelDto, Object>> fieldMapFunction() {
        return Map.of(
                "itemCode", MenuItemExcelDto::getItemCode,
                "itemId", MenuItemExcelDto::getItemId,
                "itemName", MenuItemExcelDto::getItemName,
                "categoryId", MenuItemExcelDto::getCategoryId,
                "isVeg", MenuItemExcelDto::isVeg,
                "error", MenuItemExcelDto::getErrorMsg
        );
    }

}
