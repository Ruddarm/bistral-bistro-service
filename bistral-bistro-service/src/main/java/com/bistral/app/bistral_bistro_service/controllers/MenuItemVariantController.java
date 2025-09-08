package com.bistral.app.bistral_bistro_service.controllers;

import com.bistral.app.bistral_bistro_service.dtos.MenuItemResponse;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import com.bistral.app.bistral_bistro_service.service.MenuItemVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("bistros/menus/menuItems/{itemId}/variants")
public class MenuItemVariantController {

    private final ModelMapper modelMapper;
    private final MenuItemVariantService menuItemVariantService;

    @PostMapping
    public ResponseEntity<MenuItemVariantResponse> createItemVariants(@Valid @RequestBody MenuItemVariantRequest menuItemVariantRequest) {
        return ResponseEntity.ok(
                modelMapper.map(menuItemVariantService.createMenuItemVariants(menuItemVariantRequest), MenuItemVariantResponse.class));
    }

    @GetMapping("/{variantsId}")
    public ResponseEntity<MenuItemVariantResponse> getItemVariants(@PathVariable UUID itemId, @PathVariable UUID variantsId) {
        return ResponseEntity.ok(
                modelMapper.map(
                        menuItemVariantService.getMenuItemVariantsById(variantsId, itemId), MenuItemVariantResponse.class));
    }

    @PatchMapping("/{variantsId}")
    public ResponseEntity<MenuItemVariantResponse> updateItemVariants(@PathVariable UUID itemId, @PathVariable UUID variantsId, @RequestBody
    Map<String, Object> updates) {
        return ResponseEntity.ok(
                menuItemVariantService.updateMenuItemVariantsById(variantsId, itemId,updates)
        );
    }


}
