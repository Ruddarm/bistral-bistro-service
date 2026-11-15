package com.bistral.app.bistral_bistro_service.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TenantOnboardingDto {

    private String tenantName;
    private String tenantSubDomain;

}
