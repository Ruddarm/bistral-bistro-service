package com.bistral.app.bistral_bistro_service.controllers;

import com.bistral.app.bistral_bistro_service.dtos.*;
import com.bistral.app.bistral_bistro_service.service.MenuItemCategoryService;
import com.bistral.app.bistral_bistro_service.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bistros/{bistroId}/menus")
public class MenuController {

    private final ModelMapper modelMapper;
    private final MenuService menuService;


    @PostMapping
    public ResponseEntity<MenuResponse> createMenus(@Valid @RequestBody MenuRequest menuRequest) {
        return ResponseEntity.ok(menuService.createMenu(menuRequest));
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponse> getMenus(@PathVariable UUID bistroId, @PathVariable UUID menuId) {
        return ResponseEntity.ok(modelMapper.map(menuService.findByMenuIdAndBistro_BistroId(bistroId, menuId), MenuResponse.class));
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponse> updateMenus(@PathVariable UUID bistroId, @PathVariable UUID menuId, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(
                menuService.updateMenuByMenuIdAndBistroID(menuId, bistroId, updates));
    }


    @GetMapping("/{menuId}/card")
    public  ResponseEntity<MenuCardResponse> getMenuCard(@PathVariable UUID menuId){
        return  ResponseEntity.ok(menuService.getMenuCard(menuId));
    }

    @GetMapping("/{menuId}/items")
    public ResponseEntity<List<MenuItemResponse>> getMenuWithItem(@PathVariable UUID bistroId, @PathVariable UUID menuId) {
        return ResponseEntity.ok(menuService.getListOfAllMenuItemsUsingJoin(menuId, bistroId));
    }
}
