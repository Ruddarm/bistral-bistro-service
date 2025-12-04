package com.bistral.app.bistral_bistro_service.dtos;

import java.util.List;
import java.util.UUID;



public record MenuItemVariantBulkRequest(UUID menuItemId, List<UUID> itemVariantId) {
}
