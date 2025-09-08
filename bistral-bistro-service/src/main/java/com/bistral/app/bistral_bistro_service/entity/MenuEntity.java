package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "menus",
        indexes = {@Index(name = "bistroId_menuId", columnList = "menuId,bistroId",unique = true)}
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
    private String menuName;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<MenuItemEntity> menuItemEntities = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "bistroId")
    private BistroEntity bistro;

}
