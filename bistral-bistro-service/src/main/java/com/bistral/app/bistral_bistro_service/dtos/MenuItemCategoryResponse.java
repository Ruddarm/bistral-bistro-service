package com.bistral.app.bistral_bistro_service.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
public record MenuItemCategoryResponse(UUID menuId, UUID categoryId, String categoryName) {
}
