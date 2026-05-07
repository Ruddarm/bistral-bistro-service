package com.bistral.app.bistral_bistro_service.service;

import com.bistral.app.bistral_bistro_service.contexts.UserContextHolder;
import com.bistral.app.bistral_bistro_service.dtos.*;
import com.bistral.app.bistral_bistro_service.entity.BistroEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final BistroService bistroService;
    private final ModelMapper modelMapper;

    public MenuResponse createMenu(MenuRequest menuRequest) throws ResourceNotFoundException {
        BistroEntity bistro = bistroService.getBistroEntityById(UserContextHolder
                .getAuthContext().getBistroId());
        MenuEntity menuEntity = modelMapper.map(menuRequest, MenuEntity.class);
        menuEntity.setCreatedBy(UserContextHolder.getAuthContext().getUserId());
        menuEntity.setBistro(bistro);
        menuEntity.setCreatedBy(UserContextHolder.getAuthContext().getUserId());
        menuEntity = menuRepository.save(menuEntity);
        return modelMapper.map(menuEntity, MenuResponse.class);
    }

    public MenuEntity findById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "Menu not found with : " + menuId));
    }

    public MenuEntity findByMenuIdAndBistro_BistroId(UUID menuId) {
        return menuRepository.findByMenuIdAndBistro_BistroId(menuId, UserContextHolder
                        .getAuthContext().getBistroId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "Menu not found with " + menuId));
    }


    public List<MenuItemResponse> getListOfAllMenuItemsUsingJoin(UUID menuID) {
        MenuEntity menuEntity = menuRepository.findByMenuIdAndBistro_BistroId(menuID,
                UserContextHolder.getAuthContext().getBistroId()).orElseThrow(() -> new ResourceNotFoundException("Menu", "Menu not found with Id : " + menuID));
        return menuEntity
                .getMenuItemEntities()
                .stream()
                .map((menuItemEntity) -> {
                    List<MenuItemVariantResponse> variantResponseList = menuItemEntity
                            .getItemVariantEntityList()
                            .stream()
                            .map((menuItemVariantEntity) -> modelMapper.map(menuItemVariantEntity, MenuItemVariantResponse.class))
                            .toList();
                    MenuItemResponse menuItemResponse = modelMapper.map(menuItemEntity, MenuItemResponse.class);
                    menuItemResponse.setMenuItemVariantResponsesList(variantResponseList);

                    return menuItemResponse;
                }).toList();
    }

    public MenuResponse updateMenuByMenuIdAndBistroID(UUID menuId, Map<String, Object> updates) {
        MenuEntity menuEntity = findByMenuIdAndBistro_BistroId(menuId);
        Set<String> blockedUpdates = Set.of("menuItemEntities", "bistro");
        updates.forEach((key, value) -> {
            if (blockedUpdates.contains(key)) return;
            Field field = ReflectionUtils.findField(MenuEntity.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, menuEntity, value);
        });
        return modelMapper.map(
                menuRepository.save(menuEntity), MenuResponse.class
        );
    }

    public MenuCardResponse getMenuCard(UUID menuId) {
        List<MenuItemFlatRow> menuItemFlatRows = menuRepository.getMenuItemFlatRows(menuId);
        HashMap<String, List<MenuItemResponse>> items = new LinkedHashMap<>();
        HashMap<UUID, MenuItemResponse> itemMap = new HashMap<>();
        for (MenuItemFlatRow row : menuItemFlatRows) {
            System.out.println(row);
            items.putIfAbsent(row.getCategoryName() == null ? "Uncategorized" : row.getCategoryName(), new ArrayList<>());
            itemMap.putIfAbsent(row.getItemId(), MenuItemResponse.builder()
                    .itemId(row.getItemId())
                    .itemName(row.getItemName())
                    .isVeg(row.getIsVeg())
                    .categoryName(row.getCategoryName())
                    .menuItemVariantResponsesList(new ArrayList<>())
                    .build());
            if (row.getVariantId() != null) {
                itemMap.get(row.getItemId()).getMenuItemVariantResponsesList().add(MenuItemVariantResponse.builder()
                        .variantName(row.getVariantName())
                        .variantId(row.getVariantId())
                        .isTaxIncluded(row.getIsTaxIncluded())
                        .price(row.getPrice())
                        .qty(row.getQty())
                        .unit(row.getItemUnit())
                        .build());
            }
        }
        for (MenuItemResponse menuItemResponse : itemMap.values()) {

            items.get(menuItemResponse.getCategoryName()).add(menuItemResponse);
        }
        return MenuCardResponse.builder().menuId(menuId).menuItems(items).build();
    }

    public List<MenuResponse> menuListing() {
        return menuRepository.findAllMenusByBistroId(
                UserContextHolder.getAuthContext().getBistroId()
        );
    }
}
