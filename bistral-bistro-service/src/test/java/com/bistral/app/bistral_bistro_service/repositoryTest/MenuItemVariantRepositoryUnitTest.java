package com.bistral.app.bistral_bistro_service.repositoryTest;


import com.bistral.app.bistral_bistro_service.entity.BistroEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemVariantEntity;
import com.bistral.app.bistral_bistro_service.entity.enums.ItemUnit;
import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
import com.bistral.app.bistral_bistro_service.repository.MenuItemRepository;
import com.bistral.app.bistral_bistro_service.repository.MenuItemVariantRepository;
import com.bistral.app.bistral_bistro_service.repository.MenuRepository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan(basePackages = "com.bistral.app.bistral_bistro_service.entity")
@EnableJpaRepositories(basePackages = "com.bistral.app.bistral_bistro_service.repository")
public class MenuItemVariantRepositoryUnitTest {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private BistroRepository bistroRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private MenuItemVariantRepository menuItemVariantRepository;
    private BistroEntity bistroEntity = BistroEntity.builder()
            .userId(UUID.randomUUID())
            .bistroName("testing-bistro").build();
    private MenuEntity menuEntity = MenuEntity.builder().menuName("testing-meu")
            .build();
    private MenuItemEntity menuItemEntity = MenuItemEntity
            .builder()
            .itemName("Water")
            .isVeg(true)
            .build();
    private MenuItemVariantEntity menuItemVariant = MenuItemVariantEntity
            .builder()
            .unit(ItemUnit.ML)
            .qty(new BigDecimal(500))
            .price(new BigDecimal(10))
            .taxRate(new BigDecimal(0))
            .build();

    @BeforeEach
    public void createBistroAndMenu() {
        bistroEntity = bistroRepository.save(bistroEntity);
        menuEntity.setBistro(bistroEntity);
        menuEntity = menuRepository.save(menuEntity);
        menuItemEntity.setMenu(menuEntity);
        menuItemRepository.save(menuItemEntity);
        menuItemVariant.setMenuItem(menuItemEntity);
        menuItemVariantRepository.save(menuItemVariant);
    }

    @Test
    public void findByVariantIdAndMenuItem_itemIdWhenBothAreValid() {
        MenuItemVariantEntity variant = menuItemVariantRepository.findByVariantIdAndMenuItem_itemId(menuItemVariant.getVariantId(), menuItemEntity.getItemId()).orElse(null);
        assertThat(variant).isEqualTo(menuItemVariant);
    }


}
