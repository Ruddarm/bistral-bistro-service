package com.bistral.app.bistral_bistro_service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "menuItem")
@Data
@Builder
public class MenuItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID itemId;
    private String name;
    private BigDecimal price;
    private BigDecimal taxRate;
    @Column(nullable = false)
    private boolean isVeg;
    @Column(nullable = false)
    private boolean isTaxIncluded;
    @ManyToOne
    @JoinColumn(name = "menuId")
    private MenuEntity menu;
}
