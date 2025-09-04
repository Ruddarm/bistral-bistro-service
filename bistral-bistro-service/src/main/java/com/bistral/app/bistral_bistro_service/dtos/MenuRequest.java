package com.bistral.app.bistral_bistro_service.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MenuRequest {
    @NotEmpty
    private  String menuName;
    @NotNull
    private UUID bistroId;
}
