package com.bistral.app.bistral_bistro_service.service;


import com.bistral.app.bistral_bistro_service.contexts.UserContextHolder;
import com.bistral.app.bistral_bistro_service.dtos.ItemVariantFilterDto;
import com.bistral.app.bistral_bistro_service.dtos.ListingDto;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.repository.MenuItemVariantQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class MenuItemVariantQueryService {

    private final MenuItemVariantQueryRepository menuItemVariantQueryRepository;


    public List<MenuItemVariantResponse> getListOfMenuItemResponse(ListingDto<ItemVariantFilterDto> listingDto) {
        return menuItemVariantQueryRepository
                .getMenuItems(listingDto.getFilterDto(), UserContextHolder
                        .getAuthContext().getBistroId(), listingDto.getPage(), listingDto.getSize());
    }

    public List<MenuItemVariantResponse> getListOfMenuItemResponse(ItemVariantFilterDto variantFilterDto) {
        return menuItemVariantQueryRepository
                .getMenuItems(variantFilterDto,
                        UserContextHolder
                                .getAuthContext()
                                .getBistroId());
    }
}
