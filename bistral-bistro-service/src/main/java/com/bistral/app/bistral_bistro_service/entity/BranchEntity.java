package com.bistral.app.bistral_bistro_service.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne
    @JoinColumn(name = "bistroId")
    @EqualsAndHashCode.Include
    @JsonProperty("bistroId")
    private BistroEntity bistro;
    private int tables;
}
