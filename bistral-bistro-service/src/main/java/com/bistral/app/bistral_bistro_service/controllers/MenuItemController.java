package com.bistral.app.bistral_bistro_service.controllers;


import com.bistral.app.bistral_bistro_service.dtos.MenuItemRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemResponse;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.entity.ImportJob;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
//import com.bistral.app.bistral_bistro_service.service.MenuItemAndVariantExcelImport;
import com.bistral.app.bistral_bistro_service.entity.enums.JobStatus;
import com.bistral.app.bistral_bistro_service.service.ImportJob.Services.ImportJobService;
import com.bistral.app.bistral_bistro_service.service.MenuItemService;
import com.bistral.app.bistral_bistro_service.service.menuItem.service.MenuItemExcelImporter;
import com.bistral.app.bistral_bistro_service.service.menuItem.service.MenuItemExcelImporterAsync;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final MenuItemExcelImporter menuItemExcelImporter;
    private final ImportJobService importJobService;
//    private final NotificationController notificationController;
    private final MenuItemExcelImporterAsync menuItemExcelImporterAsync;
//    private final MenuItemAndVariantExcelImport menuItemAndVariantExcelImport;

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
        menuItemResponse.setCategoryId(menuItemEntity.getMenuItemCategory().getCategoryId());
        menuItemResponse.setCategoryName(menuItemEntity.getMenuItemCategory().getCategoryName());
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

    @PostMapping("/import/excel")
    public void importMenuItemExcel(@PathVariable("menuId") UUID menuId, HttpServletResponse response, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Workbook wb = menuItemExcelImporter.importItem(menuId, multipartFile);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=errorResponse.xlsx");
        wb.write(response.getOutputStream());
        wb.close();
    }

    @PostMapping("/import/async/excel")
    public ResponseEntity<ImportJob> importMenuItemAsync(@PathVariable("menuId") UUID menuId, @RequestParam("file") MultipartFile multipartFile) throws InterruptedException {
        ImportJob importJob = importJobService.createImportJob(ImportJob.builder().jobStatus(JobStatus.PENDING).build());
        menuItemExcelImporterAsync.ImportFromExcel(menuId, multipartFile);
        return ResponseEntity.ok(importJob);
    }

}
