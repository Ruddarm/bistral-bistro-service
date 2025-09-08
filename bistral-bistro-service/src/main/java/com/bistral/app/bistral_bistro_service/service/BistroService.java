package com.bistral.app.bistral_bistro_service.service;

import com.bistral.app.bistral_bistro_service.contexts.UserContext;
import com.bistral.app.bistral_bistro_service.dtos.BistroRequest;
import com.bistral.app.bistral_bistro_service.dtos.BistroResponse;
import com.bistral.app.bistral_bistro_service.dtos.BranchResponse;
import com.bistral.app.bistral_bistro_service.dtos.MenuResponse;
import com.bistral.app.bistral_bistro_service.entity.BistroEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.mapperInterface.BistroMapper;
import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BistroService {

    private final BistroMapper bistroMapper;
    private final ModelMapper modelMapper;
    private final BistroRepository bistroRepository;

    public BistroResponse createBistro(BistroRequest bistroRequest) {
        BistroEntity bistroEntity = bistroMapper.toEntity(bistroRequest);
        // TODO : User user form user context after authentciaton is implemented
        bistroEntity.setUserId(UUID.randomUUID());
        bistroRepository.save(bistroEntity);
        return bistroMapper.toResponse(bistroEntity);
    }


    public List<BistroResponse> getAllBistroOfUser() {
        List<BistroEntity> bistroEntityList = bistroRepository.findByUserId(UserContext.getUserContext());
        return bistroEntityList
                .stream()
                .map((bistro) -> modelMapper.map(bistro, BistroResponse.class))
                .collect(Collectors.toList());
    }

    public BistroEntity getBistroEntityById(UUID bistroId) throws ResourceNotFoundException {
        return bistroRepository.findById(bistroId)
                .orElseThrow(() -> new ResourceNotFoundException("Bistro", "Bistro not found with Id " + bistroId));
    }

    public BistroResponse getBistroResponseByBistroID(UUID bistroId) throws ResourceNotFoundException {
        return bistroRepository.findByBistroId(bistroId).orElseThrow(
                () -> new ResourceNotFoundException("Bistro", "Bistro not found with Id " + bistroId)
        );
    }

    public Boolean existByBistroId(UUID bistroId) {
        return bistroRepository.existsById(bistroId);
    }

    /*
        A method to get a list of all branch for a particular bistro
        @Param bistroId
        @Return @link{List<BranchResponse>} return a list of all branches
     */
    public List<BranchResponse> getListOfBranches(UUID bistroId) throws ResourceNotFoundException {
        BistroEntity bistroEntity = bistroRepository.findBistroWithBranches(bistroId).orElseThrow(
                () -> new ResourceNotFoundException("", "bistro not found")
        );
        return bistroEntity.getBranches()
                .stream()
                .map((branch) -> modelMapper.map(branch, BranchResponse.class))
                .collect(Collectors.toList());
    }

    /*
        A method to get a list of all menus for a bistro
     */
    public List<MenuResponse> getListOfMenus(UUID bistroId) throws ResourceNotFoundException {
        BistroEntity bistroEntity = getBistroEntityById(bistroId);
        return bistroEntity.getMenuEntities()
                .stream()
                .map((menu) -> modelMapper.map(menu, MenuResponse.class))
                .collect(Collectors.toList());
    }
}
