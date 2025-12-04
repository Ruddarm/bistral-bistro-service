package com.bistral.app.bistral_bistro_service.service.menu.interfaces;

import com.bistral.app.bistral_bistro_service.dtos.MenuCardResponse;

import java.util.UUID;

public interface MenuQueryService {

    MenuCardResponse getCardResponse(UUID menuId);
}
