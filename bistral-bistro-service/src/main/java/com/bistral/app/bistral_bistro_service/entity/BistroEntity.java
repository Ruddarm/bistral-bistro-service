package com.bistral.app.bistral_bistro_service.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@Table(name = "bistros")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class BistroEntity {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bistro_id")
    private UUID bistroId;

    @Column(name = "bistro_name", nullable = false)
    private String bistroName;
    private String logoUrl;

    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "updatedBy")
    private UUID updatedBy;

    @OneToMany(mappedBy = "bistro", orphanRemoval = true, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<BranchEntity> branches;
    @OneToMany(mappedBy = "bistro", orphanRemoval = true, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<MenuEntity> menuEntities;
}
