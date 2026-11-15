package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

/**
 *
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity()
public class TenantOnboardingHistory extends SoftDeletableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tenantOnboardingHistoryId;

    private String action;

    @ManyToOne()
    @JoinColumn(name = "tenant_onboarding_id",nullable = false)
    private TenantOnboarding tenantOnboarding;
}
