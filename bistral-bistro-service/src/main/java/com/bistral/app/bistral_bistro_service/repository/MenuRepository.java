package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, UUID> {

    //    @Query(
//            "Select DISTINCT m from MenuEntity m " +
//                    "LEFT JOIN FETCH m.menuItemEntities i " +
//                    "LEFT JOIN FETCH i.itemVariantEntityList " +
//                    "Where m.menuId = :menuId"
//    )

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
}
