package com.bistral.app.bistral_bistro_service.service.menuItemVariant.interfaces;


import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMenuItemVariantBulkCurdService {

    List<MenuItemVariantEntity> addMany();
}
