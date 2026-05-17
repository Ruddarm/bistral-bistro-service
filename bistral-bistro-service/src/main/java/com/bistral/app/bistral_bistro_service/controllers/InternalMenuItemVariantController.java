package com.bistral.app.bistral_bistro_service.controllers;


import com.bistral.app.bistral_bistro_service.dtos.ApiResponse;
import com.bistral.app.bistral_bistro_service.dtos.ItemVariantFilterDto;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.service.MenuItemVariantQueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("bistros/internal/menu/item/variants")
public class InternalMenuItemVariantController {

    private final MenuItemVariantQueryService menuItemVariantQueryService;

    @PostMapping()
    ApiResponse<List<MenuItemVariantResponse>> getMenuItemVariantsResponse(@RequestBody ItemVariantFilterDto itemVariantFilterDto) {
        return
                ApiResponse.<List<MenuItemVariantResponse>>ok(
                        menuItemVariantQueryService.getListOfMenuItemResponse(itemVariantFilterDto),
                        "Variants found Successfully");
    }

}
