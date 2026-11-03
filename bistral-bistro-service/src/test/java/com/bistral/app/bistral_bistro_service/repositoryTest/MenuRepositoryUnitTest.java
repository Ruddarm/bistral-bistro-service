package com.bistral.app.bistral_bistro_service.repositoryTest;


import com.bistral.app.bistral_bistro_service.entity.Bistro;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuItemEntity;
import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
import com.bistral.app.bistral_bistro_service.repository.MenuRepository;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EntityScan(basePackages = "com.bistral.app.bistral_bistro_service.entity")
@EnableJpaRepositories(basePackages = "com.bistral.app.bistral_bistro_service.repository")
public class MenuRepositoryUnitTest {

    @Autowired
    BistroRepository bistroRepository;

    @Autowired
    MenuRepository menuRepository;

    private Bistro bistro;
    private MenuEntity menuEntity;

    @BeforeEach
    public void createBistro() {
        bistro = Bistro.builder()
                .bistroName("localBistro")
                .userId(UUID.randomUUID())
                .build();
        bistro = bistroRepository.save(bistro);
        menuEntity = MenuEntity.builder()
                .menuName("testing Menu")
                .bistro(bistro)
                .build();
        menuEntity = menuRepository.save(menuEntity);
    }


    @Test
    public void testFindByMenuIdAndBistro_BistroId_whenBothAreValid() {

        MenuEntity menu   = menuRepository.findByMenuIdAndBistro_BistroId(menuEntity.getMenuId(), bistro.getBistroId()).orElse(null);
        assertThat(menu)
                .isNotNull()
                .isInstanceOf(MenuEntity.class);
        assertThat(menu.getMenuId()).isEqualTo(menuEntity.getMenuId());
        assertThat(menu.getBistro().getBistroId())
                .isEqualTo(bistro.getBistroId());
        for(MenuItemEntity item : menu.getMenuItemEntities()){
            System.out.println(item);
        }

    }



}
