package com.bistral.app.bistral_bistro_service.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MenuResponse {
    private UUID menuId;
    private String menuName;
    private UUID bistroId;
}
