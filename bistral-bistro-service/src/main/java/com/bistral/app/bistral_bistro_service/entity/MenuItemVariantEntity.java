package com.bistral.app.bistral_bistro_service.entity;

import com.bistral.app.bistral_bistro_service.entity.enums.ItemUnit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_item_variant",
        indexes = {
                @Index(name = "variantId_menu_item_id", columnList = "variantId, menu_item_id", unique = true)
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class MenuItemVariantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID variantId;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxRate;
    @Column(nullable = false)
    private boolean isTaxIncluded;
    @Column(nullable = false)
    private BigDecimal qty;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemUnit unit;
    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItemEntity menuItem;

    @Override
    public String toString() {
        return String.format("Variant Id : %s \n price : %s \n taxRate : %s \n  Qty : %s \n Unit : %s \n Item Id : %s",
                this.variantId,
                this.price,
                this.taxRate,
                this.qty,
                this.unit,
                this.menuItem.getItemId()
        );
    }


}
