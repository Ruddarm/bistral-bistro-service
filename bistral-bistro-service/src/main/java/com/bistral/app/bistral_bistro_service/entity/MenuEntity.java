package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="menus")
@Data
@Builder
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID menuId;
    private  String name;
    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private List<MenuItemEntity> menuItemEntities;
    @ManyToOne
    @JoinColumn(name = "bistroId")
    private BistroEntity bistro;

}
