package com.bistral.app.bistral_bistro_service.entity;


import com.bistral.app.bistral_bistro_service.enums.TenantOnboardingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 */
@Builder
@Getter
@Setter
@Entity
public class TenantOnboarding extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID onboardingId;

    @OneToOne()
    @JoinColumn(nullable = false, name = "tenant_id")
    private Tenant tenant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private TenantOnboardingStatus tenantOnboardingStatus = TenantOnboardingStatus.TENANT_CREATED;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<TenantOnboardingHistory> tenantOnboardingHistories = new ArrayList<>();

    /**
     *
     * @param tenantOnboardingHistory
     */
    public void addTenantOnboardingHistory(@NonNull TenantOnboardingHistory tenantOnboardingHistory) {
        if (tenantOnboardingHistory.getTenantOnboarding() != null && tenantOnboardingHistory.getTenantOnboarding() != this) {
            throw new IllegalStateException("History is already associated with another onboarding");
        }
        if (tenantOnboardingHistories.contains(tenantOnboardingHistory)) return;
        tenantOnboardingHistory.setTenantOnboarding(this);
        this.tenantOnboardingHistories.add(tenantOnboardingHistory);
    }

}
