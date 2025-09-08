package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
