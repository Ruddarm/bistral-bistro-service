package com.bistral.app.bistral_bistro_service.entity;

import com.bistral.app.bistral_bistro_service.entity.enums.ItemUnit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    @OneToMany(mappedBy = "menuItem", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<MenuItemVariantEntity> itemVariantEntityList = new HashSet<>();
}
