package com.bistral.app.bistral_bistro_service.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
@Entity
@Table(name="bistro_branch")
@Data
@Builder
public class BranchEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID branchId;

    @Column(nullable = false)
    private String branchName;

    @Column(nullable = true)
    private String Address;

    @ManyToOne
    private BistroEntity bistro;

    private int tabels;
}
