package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.dtos.MenuItemFlatRow;
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

    //    @EntityGraph(attributePaths = {
//            "menuItemEntities",
//            "menuItemEntities.itemVariantEntityList",
//            "menuItemEntities.menuItemCategory"
//    })
//    @Query(value = """
//
//            SELECT
//                i.item_id,
//                i.item_name,
//                i.is_veg,
//                i.menu_id,
//                c.category_id,
//                c.category_name,
//                v.variant_id,
//                v.price,
//                v.tax_rate,
//                v.qty,
//                v.unit,
//                v.is_tax_included
//            FROM menu_item i
//            LEFT JOIN menu_item_category c ON c.category_id = i.category_id
//            LEFT JOIN menu_item_variant v ON v.menu_item_id = i.item_id
//            WHERE i.menu_id = :menuId
//            ORDER BY
//                COALESCE(c.category_name, 'ZZZ') ASC,
//                i.item_name ASC
//            """,nativeQuery = true)
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
}
