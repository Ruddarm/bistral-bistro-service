package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "menu_item_category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID categoryId;
    @Column(nullable = false)
    String categoryName;
    @JoinColumn(name = "menuId")
    @ManyToOne(fetch = FetchType.LAZY)
    MenuEntity menuEntity;

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
