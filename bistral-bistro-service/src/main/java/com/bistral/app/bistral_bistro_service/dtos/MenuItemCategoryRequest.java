package com.bistral.app.bistral_bistro_service.dtos;

import java.util.UUID;

public record MenuItemCategoryRequest(UUID menuId, String categoryName) {
}
