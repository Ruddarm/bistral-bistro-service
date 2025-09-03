package com.bistral.app.bistral_bistro_service.entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Builder
@Data
@Table(name = "Bistro")
public class BistroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bistroId;
    @Column(nullable = false)
    private String bistroName;
    @Column(nullable = true)
    private  String logoUrl;
    @Column(nullable = true)
    private String Address;
    @Column(nullable = false)
    private UUID userId;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy="bistro")
    private List<BranchEntity> branches;
    @OneToMany(mappedBy = "bistro")
    private List<MenuEntity> menuEntities;
}
