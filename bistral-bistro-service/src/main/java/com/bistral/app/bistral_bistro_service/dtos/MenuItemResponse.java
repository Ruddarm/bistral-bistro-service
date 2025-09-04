package com.bistral.app.bistral_bistro_service.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MenuItemResponse {

    private UUID MenuItemId;
    private String itemName;

}
