package com.bistral.app.bistral_bistro_service.controllers;


import com.bistral.app.bistral_bistro_service.dtos.MenuItemCategoryRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemCategoryResponse;
import com.bistral.app.bistral_bistro_service.service.MenuItemCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/bistros/menu-category")
public class MenuItemCategoryController {

    private final MenuItemCategoryService menuItemCategoryService;

    @PostMapping()
    ResponseEntity<MenuItemCategoryResponse> createCategory(@Valid @RequestBody MenuItemCategoryRequest menuItemCategoryRequest) {
        return ResponseEntity.ok(menuItemCategoryService.createCategory(menuItemCategoryRequest));
    }

    @GetMapping("/all/{menuId}")
    ResponseEntity<List<MenuItemCategoryResponse>>  getAllCategory(@PathVariable UUID menuId){
        return  ResponseEntity.ok(menuItemCategoryService.findAllByMenuId(menuId));
    }
}
