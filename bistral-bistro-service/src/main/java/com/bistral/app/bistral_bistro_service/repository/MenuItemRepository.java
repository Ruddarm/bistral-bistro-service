package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, UUID> {


    @EntityGraph(
            attributePaths = {
                    "itemVariantEntityList"
            }
    )
    public Optional<MenuItemEntity> findByItemIdAndMenu_MenuId(@Param("itemId") UUID itemId, @Param("menuID") UUID menuId);

    @Query(
            """
               SELECT DISTINCT mi from MenuItemEntity mi 
               LEFT JOIN FETCH mi.itemVariantEntityList v
               Where mi.menu.menuId = :menuId AND Lower(mi.itemName)
               LIKE LOWER(CONCAT('%',:keyword,'%'))       
            """
    )
    Page<MenuItemEntity> searchMenuItemsByMenuWithVariants(@Param("menuId") UUID menuId,@Param("keyword") String keyword, Pageable pageable);
}
