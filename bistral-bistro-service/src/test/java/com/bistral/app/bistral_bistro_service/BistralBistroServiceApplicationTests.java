package com.bistral.app.bistral_bistro_service;

import com.bistral.app.bistral_bistro_service.dtos.*;
import com.bistral.app.bistral_bistro_service.enums.ItemUnit;
import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
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
    private BistroService bistroService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private BistroRepository bistroRepository;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuItemVariantService menuItemVariantService;

    @Test
    void contextLoads() {
    }


    @Test
    public void testGetMenuUsingJoin() {
        BistroRequest bistroRequest = BistroRequest.builder()
                .bistroName("localBistro")
                .build();
        BistroResponse bistroResponse = bistroService.createBistro(bistroRequest);
        MenuRequest menuRequest = MenuRequest.builder()
                .bistroId(bistroResponse.getBistroId())
                .menuName("Dinner v1")
                .build();
        MenuResponse menuResponse = menuService.createMenu(menuRequest);

        MenuItemRequest menuItemRequest = MenuItemRequest.builder()
                .menuId(menuResponse.getMenuId())
                .itemName("coke")
                .isVeg(true)
                .build();
        MenuItemResponse menuItemResponse = menuItemService.createMenuItem(menuItemRequest);

        MenuItemVariantRequest variantOne = MenuItemVariantRequest
                .builder()
                .itemId(menuItemResponse.getItemId())
                .qty(new BigDecimal(250))
                .unit(ItemUnit.ML)
                .price(new BigDecimal(20))
                .taxRate(new BigDecimal(0))
                .isTaxIncluded(false)
                .build();
        MenuItemVariantRequest variantTwo = MenuItemVariantRequest
                .builder()
                .itemId(menuItemResponse.getItemId())
                .qty(new BigDecimal(500))
                .unit(ItemUnit.ML)
                .price(new BigDecimal(40))
                .taxRate(new BigDecimal(0))
                .isTaxIncluded(false)
                .build();
        menuItemVariantService.createMenuItemVariants(variantTwo);
        menuItemVariantService.createMenuItemVariants(variantOne);

        List<MenuItemResponse> menu = menuService.getListOfAllMenuItemsUsingJoin(menuResponse.getMenuId(), bistroResponse.getBistroId());
        for (MenuItemResponse response : menu)
            System.out.println(response);
    }


    @Test
    public void testingRepository() {

        bistroRepository
                .getBistroContext(List.of(UUID.fromString("fbaf6532-2dbd-4566-a297-8fb5a83c54ba")),
                        List.of(UUID.fromString("c2280862-63ae-4503-9dc8-fa39988bf936")
                                , UUID.fromString(
                                        "096199b1-5eff-48d5-8439-3722e42c5d93"))
                ).forEach(System.out::println);

    }
}
