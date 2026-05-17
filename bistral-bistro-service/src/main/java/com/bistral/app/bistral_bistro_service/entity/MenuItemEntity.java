package com.bistral.app.bistral_bistro_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "menu_item",
        indexes =
                {
                        @Index(name = "itemId_menuId", columnList = "itemId, menuId", unique = true)
                }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"itemVariantEntityList"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class MenuItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID itemId;
    @Column(nullable = false)
    private String itemName;
    @Column(nullable = false)
    private boolean isVeg;
    @ManyToOne
    @JoinColumn(name = "menuId")
    private MenuEntity menu;
    @OneToMany(mappedBy = "menuItem", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<MenuItemVariantEntity> itemVariantEntityList = new ArrayList<>();
    @JoinColumn(name = "category_Id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MenuItemCategoryEntity menuItemCategory;

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
