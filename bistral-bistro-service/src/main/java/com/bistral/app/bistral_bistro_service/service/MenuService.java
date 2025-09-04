package com.bistral.app.bistral_bistro_service.service;

import com.bistral.app.bistral_bistro_service.dtos.MenuRequest;
import com.bistral.app.bistral_bistro_service.dtos.MenuResponse;
import com.bistral.app.bistral_bistro_service.entity.BistroEntity;
import com.bistral.app.bistral_bistro_service.entity.MenuEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final BistroService bistroService;
    private final ModelMapper modelMapper;

    @Transactional
    public MenuResponse createMenu(MenuRequest menuRequest) throws ResourceNotFoundException {
        BistroEntity bistro = bistroService.getBistroEntityById(menuRequest.getBistroId());
        MenuEntity menuEntity = modelMapper.map(menuRequest, MenuEntity.class);
        menuEntity.setBistro(bistro);
        menuEntity = menuRepository.save(menuEntity);
        return modelMapper.map(menuEntity, MenuResponse.class);
    }

    public MenuEntity getMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
                                .orElseThrow(() -> new ResourceNotFoundException("Menu", "Menu not found with " + menuId));
    }

}
