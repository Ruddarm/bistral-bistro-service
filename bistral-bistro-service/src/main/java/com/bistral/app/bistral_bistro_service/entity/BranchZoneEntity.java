package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

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

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JoinColumn(name = "branch_id")
    @ManyToOne()
    private BranchEntity branch;


    @OneToMany(mappedBy = "zone")
    private List<TableEntity> tables = new ArrayList<>();


}
