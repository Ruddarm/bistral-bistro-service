package com.bistral.app.bistral_bistro_service.controllers;

import com.bistral.app.bistral_bistro_service.dtos.MenuItemResponse;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantBulkRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import com.bistral.app.bistral_bistro_service.mapperInterface.MenuItemVariantMapper;
import com.bistral.app.bistral_bistro_service.service.MenuItemVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("bistros/menus/{menuId}/menu-item/{itemId}/variants")
public class MenuItemVariantController {

    private final ModelMapper modelMapper;
    private final MenuItemVariantService menuItemVariantService;
    private final MenuItemVariantMapper menuItemVariantMapper;

    @PostMapping
    public ResponseEntity<MenuItemVariantResponse> createItemVariants(
            @PathVariable UUID menuId, @PathVariable UUID itemId, @Valid @RequestBody MenuItemVariantRequest menuItemVariantRequest) {
        return ResponseEntity.ok(
                menuItemVariantMapper.toVariantResponse(menuItemVariantService.createMenuItemVariants(menuId,itemId,menuItemVariantRequest)));
    }

    @Deprecated
    @PostMapping("/bulk")
    public ResponseEntity<List<MenuItemVariantResponse>> getItemVariants(@Valid @RequestBody MenuItemVariantBulkRequest menuItemVariantBulkRequests) {
        return ResponseEntity.ok(menuItemVariantService.getMenuItemVariantBulk(menuItemVariantBulkRequests));
    }

    @GetMapping("/{variantsId}")
    public ResponseEntity<MenuItemVariantResponse> getItemVariants(@PathVariable UUID itemId, @PathVariable UUID variantsId) {
        MenuItemVariantResponse menuItemVariantResponse = menuItemVariantService.getMenuItemVariantsById(variantsId, itemId);
        return ResponseEntity.ok(menuItemVariantResponse);
    }


    @PatchMapping("/{variantsId}")
    public ResponseEntity<MenuItemVariantResponse> updateItemVariants(@PathVariable UUID itemId, @PathVariable UUID variantsId, @RequestBody
    Map<String, Object> updates) {
        return ResponseEntity.ok(
                menuItemVariantService.updateMenuItemVariantsById(variantsId, itemId, updates)
        );
    }





}
