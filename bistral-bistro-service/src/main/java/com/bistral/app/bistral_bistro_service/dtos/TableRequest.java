package com.bistral.app.bistral_bistro_service.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class TableRequest {
    private UUID zoneId;
    private int count;
    @NotNull
    private UUID branchId;
}
