package com.bistral.app.bistral_bistro_service.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tenantId;

    @Column(nullable = false)
    private String tenantName;

    @OneToMany(mappedBy = "tenant",cascade =  CascadeType.ALL , fetch = FetchType.LAZY)
    List<Bistro> bistros;
}

