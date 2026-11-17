package com.bistral.app.bistral_bistro_service.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TenantOnboardingDto {

    @NotBlank
    private String tenantName;
    @NotBlank
    private String tenantSubDomain;

}
