package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "branch_zones")
public class BranchZoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID zone_id;

    @Column(name = "name")
    private  String zoneName;

    @Column
    @CreationTimestamp
    LocalDateTime created_at;

    @JoinColumn(name = "branch_id")
    @ManyToOne()
    BranchEntity branch;


    @OneToMany( mappedBy = "zone")
    List<TableEntity> tables = new ArrayList<>();


}
