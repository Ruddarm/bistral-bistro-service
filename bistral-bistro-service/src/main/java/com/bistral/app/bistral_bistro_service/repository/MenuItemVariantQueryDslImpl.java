package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.dtos.ItemVariantFilterDto;
import com.bistral.app.bistral_bistro_service.dtos.MenuItemVariantResponse;
import com.bistral.app.bistral_bistro_service.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class MenuItemVariantQueryDslImpl implements MenuItemVariantQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<MenuItemVariantResponse> getMenuItems(ItemVariantFilterDto itemVariantFilterDto, UUID bistroId, Integer page, Integer size) {
        QMenuItemVariantEntity qMenuItemVariant = QMenuItemVariantEntity.menuItemVariantEntity;
        QMenuItemEntity qMenuItem = QMenuItemEntity.menuItemEntity;
        QMenuEntity qMenu = QMenuEntity.menuEntity;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (itemVariantFilterDto != null) {
            if (itemVariantFilterDto.getVariantIds() != null && !itemVariantFilterDto.getVariantIds().isEmpty()) {
                booleanBuilder.and(
                        qMenuItemVariant.variantId.in(itemVariantFilterDto.getVariantIds())
                );
            }
        }
        booleanBuilder.and(qMenu.bistro.bistroId.eq(bistroId));
        JPAQuery<MenuItemVariantResponse> query = jpaQueryFactory
                .select(
                        Projections.constructor(MenuItemVariantResponse.class,
                                qMenuItem.itemId,
                                qMenuItem.itemName,
                                qMenuItemVariant.variantName,
                                qMenuItemVariant.variantId,
                                qMenuItemVariant.price,
                                qMenuItemVariant.taxRate,
                                qMenuItemVariant.isTaxIncluded,
                                qMenuItemVariant.qty,
                                qMenuItemVariant.unit,
                                qMenu.menuId

                        )
                )
                .from(qMenuItemVariant)
                .leftJoin(qMenuItemVariant.menuItem, qMenuItem)
                .leftJoin(qMenuItem.menu, qMenu)
                .where(
                        booleanBuilder
                );
        if (size != null && page != null) {
            query.offset((long) size * page);
            query.limit(size);
        }
        return query.distinct().fetch();
    }

    @Override
    public List<MenuItemVariantResponse> getMenuItems(ItemVariantFilterDto itemVariantFilterDto, UUID bistroId) {
        return getMenuItems(itemVariantFilterDto, bistroId, null, null);
    }

}

