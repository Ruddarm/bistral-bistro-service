package com.bistral.app.bistral_bistro_service.service;

import com.bistral.app.bistral_bistro_service.contexts.UserContextHolder;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantBulkRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.mapperInterface.MenuItemVariantMapper;
import com.bistral.app.bistral_bistro_service.repository.MenuItemVariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuItemVariantService {
    private final MenuItemService menuItemService;
    private final MenuItemVariantRepository menuItemVariantRepository;
    private final ModelMapper modelMapper;
    private final MenuItemVariantMapper menuItemVariantMapper;
//    private Map<UUID, MenuItemVariantResponse> menuItemVariantResponseHashMap;

    public MenuItemVariantEntity createMenuItemVariants(MenuItemVariantRequest menuItemVariantRequest) {
        MenuItemEntity menuItemEntity = menuItemService.getMenuItemEntityById(menuItemVariantRequest.getMenuId(), menuItemVariantRequest.getItemId());
        MenuItemVariantEntity variantEntity = menuItemVariantMapper.toVariantEntity(menuItemVariantRequest);
        variantEntity.setMenuItem(menuItemEntity);
        variantEntity.setCreatedBy(UserContextHolder.getAuthContext().getUserId());
        return menuItemVariantRepository.save(variantEntity);
    }

    public MenuItemVariantResponse getMenuItemVariantsById(UUID variantId, UUID itemId) throws ResourceNotFoundException {
        return menuItemVariantRepository.findByVariantIdAndMenuItem_itemId(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Item Variant", "Item variant is not found with Id : " + variantId));
    }

    public List<MenuItemVariantResponse> getMenuItemVariantBulk(MenuItemVariantBulkRequest menuItemVariantBulkRequestList) {
            return   menuItemVariantRepository
                    .getAllVariantList()
                    .stream()
                    .map((menuItemVariant) -> {
                        MenuItemVariantResponse res = menuItemVariantMapper.toVariantResponse(menuItemVariant);
                        res.setItemId(menuItemVariant.getMenuItem().getItemId());
                        res.setItemName(menuItemVariant.getMenuItem().getItemName());
                        return res;
                    }).toList();
    }


    public MenuItemVariantResponse updateMenuItemVariantsById(UUID variantId, UUID itemId, Map<String, Object> updates) {
        MenuItemVariantEntity menuItemVariantEntity = menuItemVariantRepository.findById(variantId).orElseThrow(() ->
                new ResourceNotFoundException("MenuItem Variant", "variant not found with Id : " + variantId));
        Set<String> blockedUpdates = Set.of("menuItem");
        updates.forEach((key, value) -> {
            if (blockedUpdates.contains(key)) return;
            Field field = ReflectionUtils.findField(MenuItemVariantEntity.class, key);
            field.setAccessible(true);
            if (field.getType() == BigDecimal.class) {
                ReflectionUtils.setField(field, menuItemVariantEntity, new BigDecimal(value.toString()));
            } else
                ReflectionUtils.setField(field, menuItemVariantEntity, value);
        });
        return modelMapper.map(
                menuItemVariantRepository.save(menuItemVariantEntity),
                MenuItemVariantResponse.class);
    }

}
