package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.dtos.MenuItemFlatRow;
import com.bistral.app.bistral_bistro_service.dtos.MenuResponse;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, UUID> {


    @EntityGraph(attributePaths = {
            "menuItemEntities",
            "menuItemEntities.itemVariantEntityList"
    })
    Optional<MenuEntity> findByMenuId(@Param("menuId") UUID menuId);

    @EntityGraph(attributePaths = {
            "menuItemEntities",
            "menuItemEntities.itemVariantEntityList"
    })
    Optional<MenuEntity> findByMenuIdAndBistro_BistroId(@Param("menuId") UUID menuId, @Param("bistroId") UUID bistroId);

    @Query(value = """
            SELECT 
                item.item_id AS itemId,
                item.item_name AS itemName,
                item.is_veg AS isVeg,
                item.category_id AS categoryId,
                cat.category_name AS categoryName,
                variant.variant_id AS variantId,
                variant.variant_name As variantName,
                variant.price AS price,
                variant.is_tax_included As isTaxIncluded,
                variant.qty As qty,
                variant.unit as unit
            FROM menu_item item
            LEFT JOIN menu_item_category cat ON item.category_id = cat.category_id
            LEFT JOIN menu_item_variant variant ON item.item_id = variant.menu_item_id
            WHERE item.menu_id = :menuId
            ORDER BY 
                COALESCE(cat.category_name, 'ZZZ'),
                item.item_name
            """,
            nativeQuery = true)
    List<MenuItemFlatRow> getMenuItemFlatRows(UUID menuId);


    @Query("""
                SELECT new com.bistral.app.bistral_bistro_service.dtos.MenuResponse(
                    m.menuId,
                    m.menuName,
                    b.bistroId,
                    b.bistroName
                )
                FROM MenuEntity m
                JOIN m.bistro b
                WHERE b.bistroId = :bistroId
            """)
    public List<MenuResponse> findAllMenusByBistroId(UUID bistroId);
}
