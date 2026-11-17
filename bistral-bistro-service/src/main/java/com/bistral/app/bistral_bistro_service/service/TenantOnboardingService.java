package com.bistral.app.bistral_bistro_service.service;

import com.bistral.app.bistral_bistro_service.annotation.HasPermission;
import com.bistral.app.bistral_bistro_service.dtos.TenantOnboardingDto;
import com.bistral.app.bistral_bistro_service.entity.SubDomainMapping;
import com.bistral.app.bistral_bistro_service.entity.Tenant;
import com.bistral.app.bistral_bistro_service.entity.TenantOnboarding;
import com.bistral.app.bistral_bistro_service.entity.TenantOnboardingHistory;
import com.bistral.app.bistral_bistro_service.enums.Resource;
import com.bistral.app.bistral_bistro_service.enums.TenantOnboardingStatus;
import com.bistral.app.bistral_bistro_service.enums.responseCode.TenantResponseCode;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceConflictException;
import com.bistral.app.bistral_bistro_service.repository.TenantOnboardingRepository;
import com.bistral.app.bistral_bistro_service.repository.TenantRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * This service is responsible to provide operation to onboard tenant.
 * These operation can be done by system user.
 * A system user onboard tenant while creating tenant and stage of the onboarding is {@link com.bistral.app.bistral_bistro_service.enums.TenantOnboardingStatus#TENANT_CREATED}
 *
 */
@AllArgsConstructor
@Service
public class TenantOnboardingService {

    private final TenantRepository tenantRepository;
    private final TenantOnboardingRepository tenantOnboardingRepository;
    private final SubDomainMappingService subDomainMappingService;

    /**
     * Create Tenant with given name,and subdomain.
     * DB handle constraint of that each subdomain must be unique.
     *
     * @param tenantOnboardingDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public UUID createTenant(@NonNull TenantOnboardingDto tenantOnboardingDto) throws ResourceConflictException {
        if (subDomainMappingService.isSubDomainTaken(tenantOnboardingDto.getTenantSubDomain())) {
            throw ResourceConflictException.from(Resource.SUBDOMAIN, TenantResponseCode.SUBDOMAIN_TAKEN);
        }
        SubDomainMapping subDomainMapping =
                SubDomainMapping
                        .builder()
                        .subDomain(tenantOnboardingDto.getTenantSubDomain())
                        .build();
        Tenant tenant = Tenant
                .builder()
                .tenantName(tenantOnboardingDto.getTenantName())
                .subDomainMapping(subDomainMapping)
                .build();
        subDomainMapping.setTenant(tenant);
        tenant = tenantRepository.saveAndFlush(tenant);
        TenantOnboardingHistory tenantOnboardingHistory =
                TenantOnboardingHistory.builder()
                        .action("Tenant Created")
                        .build();
        TenantOnboarding tenantOnboarding = TenantOnboarding
                .builder()
                .tenant(tenant)
                .tenantOnboardingStatus(TenantOnboardingStatus.TENANT_CREATED)
                .build();
        tenantOnboarding.addTenantOnboardingHistory(tenantOnboardingHistory);
        tenantOnboardingRepository.saveAndFlush(tenantOnboarding);
        return tenant.getTenantId();
    }


}
