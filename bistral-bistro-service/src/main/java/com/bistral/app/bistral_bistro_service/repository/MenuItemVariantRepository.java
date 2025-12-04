package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuItemVariantRepository extends JpaRepository<MenuItemVariantEntity, UUID> {

//    MenuItemVariantResponse

    @Query("""
            SELECT  new com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse(
               menuItem.id, 
               menuItem.itemName, 
               itemVariant.variantName,
               itemVariant.variantId,
               itemVariant.price,
               itemVariant.taxRate,
               itemVariant.isTaxIncluded,
               itemVariant.qty,
               itemVariant.unit
            )
            FROM MenuItemVariantEntity itemVariant 
            INNER Join itemVariant.menuItem menuItem
            WHERE itemVariant.variantId= :variantId
            """)
    Optional<MenuItemVariantResponse> findByVariantIdAndMenuItem_itemId(@Param("variantId") UUID variantId);

    @Query("Select v from MenuItemVariantEntity v INNER JOIN v.menuItem m where m.itemId= :menuItemId and v.variantId in :variantIds")
    List<MenuItemVariantEntity> getListOfItemVariant(@Param("menuItemId") UUID menuItemId, @Param("variantIds") List<UUID> variantIds);

    @Query("Select v from MenuItemVariantEntity v INNER JOIN v.menuItem m")
    List<MenuItemVariantEntity> getAllVariantList();
}
