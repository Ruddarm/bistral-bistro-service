package com.bistral.app.bistral_bistro_service.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

@Entity
@Table(name = "bistro_branch",
        indexes = {@Index(name = "branchId_bistro", columnList = "bistroId,branchId",unique = true)}
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID branchId;
    @Column(nullable = false)
    private String branchName;
    @Column(nullable = true)
    private String address = new String();
    @ManyToOne(
    fetch = FetchType.LAZY)
    @JoinColumn(name = "bistroId")
    @EqualsAndHashCode.Include
    @JsonProperty("bistroId")
    private BistroEntity bistro;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true,mappedBy = "branch")
    private List<TableEntity> tables = new ArrayList();
    @OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "branch")
    private  List<BranchZoneEntity> zones = new ArrayList<>();
}
