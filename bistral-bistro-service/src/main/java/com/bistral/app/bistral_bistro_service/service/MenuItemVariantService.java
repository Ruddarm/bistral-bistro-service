package com.bistral.app.bistral_bistro_service.service;

import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.repository.MenuItemVariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MenuItemVariantService {
    private final MenuItemService menuItemService;
    private final MenuItemVariantRepository menuItemVariantRepository;
    private final ModelMapper modelMapper;


    public MenuItemVariantEntity createMenuItemVariants(MenuItemVariantRequest menuItemVariantRequest) {
        MenuItemEntity menuItemEntity = menuItemService.getMenuItemEntityById(menuItemVariantRequest.getItemId(), menuItemVariantRequest.getMenuId());
        MenuItemVariantEntity variantEntity = modelMapper.map(menuItemVariantRequest, MenuItemVariantEntity.class);
        variantEntity.setMenuItem(menuItemEntity);
        return menuItemVariantRepository.save(variantEntity);
    }

    public MenuItemVariantEntity getMenuItemVariantsById(UUID variantId, UUID itemId) throws ResourceNotFoundException {
        return menuItemVariantRepository.findByVariantIdAndMenuItem_itemId(variantId, itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item Variant", "Item variant is not found with Id : " + variantId));
    }

    public MenuItemVariantResponse updateMenuItemVariantsById(UUID variantId, UUID itemId, Map<String, Object> updates) {
        MenuItemVariantEntity menuItemVariantEntity = getMenuItemVariantsById(variantId, itemId);
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
