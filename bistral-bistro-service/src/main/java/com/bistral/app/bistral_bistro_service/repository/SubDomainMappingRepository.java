package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.Bistro;
import com.bistral.app.bistral_bistro_service.entity.SubDomainMapping;
import com.bistral.app.bistral_bistro_service.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubDomainMappingRepository extends JpaRepository<SubDomainMapping, UUID> {


    boolean existsBySubDomainAndDeletedAtIsNull(String subdomain);

    Optional<SubDomainMapping> findBySubDomainAndDeletedAtIsNull(String subdomain);

    Optional<SubDomainMapping> findByTenantAndDeletedAtIsNull(Tenant tenant);

    Optional<SubDomainMapping> findByBistroAndDeletedAtIsNull(Bistro bistro);

}
