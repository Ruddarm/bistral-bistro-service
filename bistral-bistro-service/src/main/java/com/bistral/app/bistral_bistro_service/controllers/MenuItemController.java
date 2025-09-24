package com.bistral.app.bistral_bistro_service.controllers;


import com.bistral.app.bistral_bistro_service.dtos.MenuItemRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemResponse;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/bistros/menus/{menuId}/menu-items")
@RequiredArgsConstructor
public class MenuItemController {
    private final MenuItemService menuItemService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<MenuItemResponse> createMenuItem(@Valid @RequestBody MenuItemRequest menuItemRequest) {
        return ResponseEntity.ok(menuItemService.createMenuItem(menuItemRequest));
    }

    @GetMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponse> getMenuItem(@PathVariable UUID menuId, @PathVariable UUID menuItemId) {
        MenuItemEntity menuItemEntity = menuItemService.getMenuItemEntityById(menuId, menuItemId);
        List<MenuItemVariantResponse> itemVariantResponsesList = new ArrayList<>();
        menuItemEntity.getItemVariantEntityList()
                .forEach((menuItemVariantEntity -> {
                    itemVariantResponsesList.add(modelMapper.map(menuItemVariantEntity, MenuItemVariantResponse.class));
                }));
        MenuItemResponse menuItemResponse = modelMapper.map(menuItemEntity, MenuItemResponse.class);
        menuItemResponse.setMenuItemVariantResponsesList(itemVariantResponsesList);
        return ResponseEntity.ok(menuItemResponse);
    }

    @PatchMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable UUID menuId, @PathVariable UUID menuItemId, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(menuItemId, menuId, updates));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MenuItemResponse>> searchMenuItem(@PathVariable UUID menuId, @RequestParam String keyword,
                                                                 @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(menuItemService.serachMenuItemsByMenu(keyword, menuId, page, size).map((menuItemEntity) -> {
            MenuItemResponse menuItemResponse = modelMapper.map(menuItemEntity, MenuItemResponse.class);
            ArrayList<MenuItemVariantResponse> menuItemVariantResponses = new ArrayList<>();
            menuItemEntity.getItemVariantEntityList().stream().forEach((menuItemVariant) -> {
                menuItemVariantResponses.add(modelMapper.map(menuItemVariant, MenuItemVariantResponse.class));
            });
            menuItemResponse.setMenuItemVariantResponsesList(menuItemVariantResponses);
            return menuItemResponse;
        }));
    }

}
