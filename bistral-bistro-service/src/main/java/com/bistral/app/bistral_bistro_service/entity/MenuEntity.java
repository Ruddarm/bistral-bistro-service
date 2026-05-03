package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "menus",
        indexes = {@Index(name = "bistroId_menuId", columnList = "menu_name,bistroId", unique = true)}
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"menuItemEntities"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID menuId;
    @Column(name = "menu_name", nullable = false)
    private String menuName;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<MenuItemEntity> menuItemEntities = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "bistroId")
    private BistroEntity bistro;
    @OneToMany(mappedBy = "menuEntity")
    private List<MenuItemCategoryEntity> menuItemCategoryList;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "created_by", nullable = false)
    private UUID createdBy;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "updated_by")
    private UUID updatedBy;

}
