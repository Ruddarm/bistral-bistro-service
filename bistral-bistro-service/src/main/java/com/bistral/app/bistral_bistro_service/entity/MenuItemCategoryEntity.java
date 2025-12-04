package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.*;
import lombok.*;

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

}
