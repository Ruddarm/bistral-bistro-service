package com.bistral.app.bistral_bistro_service.service;

import com.bistral.app.bistral_bistro_service.contexts.UserContextHolder;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemCategoryRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemCategoryResponse;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemCategoryEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.repository.MenuItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MenuItemCategoryService {
    private final MenuItemCategoryRepository menuCategoryRepository;
    private final MenuService menuService;

    public MenuItemCategoryResponse createCategory(MenuItemCategoryRequest menuItemCategoryRequest) {
        MenuEntity menuEntity = menuService.findByMenuIdAndBistro_BistroId(menuItemCategoryRequest.menuId());
        MenuItemCategoryEntity menuItemCategoryEntity = MenuItemCategoryEntity
                .builder()
                .categoryName(menuItemCategoryRequest.categoryName())
                .menuEntity(menuEntity)
                .createdBy(UserContextHolder.getAuthContext().getUserId())
                .build();
        menuItemCategoryEntity = menuCategoryRepository.save(menuItemCategoryEntity);
        return MenuItemCategoryResponse.builder().categoryName(menuItemCategoryEntity.getCategoryName())
                .categoryId(menuItemCategoryEntity.getCategoryId())
                .menuId(menuEntity.getMenuId()).build();
    }

    public MenuItemCategoryEntity findById(UUID categoryId) {
        return menuCategoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Menu Category", "Menu Category not found with id  : " + categoryId));
    }

    public List<MenuItemCategoryResponse> findAllByMenuId(UUID menuId) {

        return menuCategoryRepository.findAllByMenuEntity(menuId).stream().map((menuItemCategoryEntity -> {
            return MenuItemCategoryResponse.builder().categoryName(menuItemCategoryEntity.getCategoryName())
                    .categoryId(menuItemCategoryEntity.getCategoryId())
                    .menuId(menuId).build();
        })).toList();
    }
}
