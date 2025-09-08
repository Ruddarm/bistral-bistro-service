package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuItemVariantRepository extends JpaRepository<MenuItemVariantEntity, UUID> {

     Optional<MenuItemVariantEntity> findByVariantIdAndMenuItem_itemId(@Param("variantId") UUID variantId , @Param("itemId") UUID itemId);
}
