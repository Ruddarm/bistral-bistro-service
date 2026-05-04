package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "branch_zones")
public class BranchZoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID zoneId;

    @Column(name = "name")
    private String zoneName;


    @JoinColumn(name = "branch_id")
    @ManyToOne()
    private BranchEntity branch;


    @OneToMany(mappedBy = "zone")
    private List<TableEntity> tables = new ArrayList<>();


    @CreationTimestamp
    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "createdBy", updatable = false, nullable = false)
    private UUID createdBy;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "updatedBy")
    private UUID updatedBy;


}
