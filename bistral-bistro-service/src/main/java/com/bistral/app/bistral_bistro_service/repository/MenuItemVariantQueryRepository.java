package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.dtos.ItemVariantFilterDto;
import com.bistral.app.bistral_bistro_service.dtos.ListingDto;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemVariantQueryRepository {


    List<MenuItemVariantResponse> getMenuItems(ItemVariantFilterDto itemVariantFilterDto, UUID bistroId, Integer page, Integer size);

    List<MenuItemVariantResponse> getMenuItems(ItemVariantFilterDto itemVariantFilterDto, UUID bistroId);
}
