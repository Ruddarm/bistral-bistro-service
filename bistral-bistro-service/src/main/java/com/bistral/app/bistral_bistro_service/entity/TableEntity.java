package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.*;
import lombok.*;

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
}
