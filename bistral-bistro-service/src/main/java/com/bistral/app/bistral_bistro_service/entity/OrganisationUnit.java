package com.bistral.app.bistral_bistro_service.entity;


import com.bistral.app.bistral_bistro_service.enums.OrganisationUnitType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 *
 *
 */
@Getter
@Setter
@Entity()
public class OrganisationUnit extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID organisationUnitId;

    @Column(nullable = false)
    private String unitName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrganisationUnitType organisationUnitType;



}
