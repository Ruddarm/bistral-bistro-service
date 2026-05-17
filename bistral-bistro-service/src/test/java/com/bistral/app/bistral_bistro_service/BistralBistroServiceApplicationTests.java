package com.bistral.app.bistral_bistro_service;

import com.bistral.app.bistral_bistro_service.dtos.*;
import com.bistral.app.bistral_bistro_service.enums.ItemUnit;
import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
import com.bistral.app.bistral_bistro_service.repository.MenuItemVariantQueryRepository;
import com.bistral.app.bistral_bistro_service.service.BistroService;
import com.bistral.app.bistral_bistro_service.service.MenuItemService;
import com.bistral.app.bistral_bistro_service.service.MenuItemVariantService;
import com.bistral.app.bistral_bistro_service.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BistralBistroServiceApplicationTests {

    @Autowired
    MenuItemVariantQueryRepository menuItemVariantQueryRepository;

    @Test
    void contextLoads() {

    }

    @Test
    public void TestGetMenuRepository() {
        ItemVariantFilterDto itemVariantFilterDto = ItemVariantFilterDto
                .builder()
                .variantIds(
                        List.of(uuidFrom("00ec52cd-e2a1-4cf2-ac9d-d233fe561d5b"),
                                uuidFrom("604ef032-7439-49b3-a082-22cbe795ed89"),
                                uuidFrom("838940f4-5e92-4429-8477-654ff19c4fc4")
                        )
                )
                .build();
        menuItemVariantQueryRepository.getMenuItems(
                        itemVariantFilterDto,
                        uuidFrom("fbaf6532-2dbd-4566-a297-8fb5a83c54ba")
                )
                .forEach(System.out::println);

    }

    public static UUID uuidFrom(String str) {
        return UUID.fromString(str);
    }


}
