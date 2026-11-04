package com.bistral.app.bistral_bistro_service.entity;

import com.bistral.app.bistral_bistro_service.enums.TenantStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tenantId;

    @Column(nullable = false)
    private String tenantName;

    @Builder.Default
    @Column(nullable = false)
    private TenantStatus tenantStatus = TenantStatus.INVITED;


    @OneToMany(mappedBy = "tenant",cascade =  CascadeType.ALL , fetch = FetchType.LAZY)
    List<Bistro> bistros;
}

