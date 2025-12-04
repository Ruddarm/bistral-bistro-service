package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.MenuItemCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemCategoryRepository extends JpaRepository<MenuItemCategoryEntity, UUID> {

    @Query(value = "select * from menu_item_category where menu_id = :menuId", nativeQuery = true)
    List<MenuItemCategoryEntity>  findAllByMenuEntity(UUID menuId);
}
