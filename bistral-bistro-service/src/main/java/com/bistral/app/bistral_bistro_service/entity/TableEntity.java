package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Bistro_tables"
        ,
        indexes = {@Index(name = "branchIndex", columnList = "branch")}
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID tableId;
    private int tableNo;
    @ManyToOne
    @EqualsAndHashCode.Include
    @JoinColumn(name = "branch")
    private BranchEntity branch;

    @ManyToOne
    @EqualsAndHashCode.Include
    @JoinColumn(name = "zone_id")
    private BranchZoneEntity zone;


    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "updatedBy")
    private UUID updatedBy;

}
