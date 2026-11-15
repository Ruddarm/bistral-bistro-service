package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.TenantOnboarding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TenantOnboardingRepository extends JpaRepository<TenantOnboarding, UUID> {
}
