package com.bistral.app.bistral_bistro_service.service;


import com.bistral.app.bistral_bistro_service.dtos.BranchRequest;
import com.bistral.app.bistral_bistro_service.dtos.BranchResponse;
import com.bistral.app.bistral_bistro_service.entity.BistroEntity;
import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.mapperInterface.BranchMapper;
import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
import com.bistral.app.bistral_bistro_service.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BranchService {
    private final ModelMapper modelMapper;
    private final BranchMapper branchMapper;
    private final BranchRepository branchRepository;
    private final BistroService bistroService;

    public BranchResponse createBranch(BranchRequest branchRequest) throws ResourceNotFoundException {
        if (bistroService.existByBistroId(branchRequest.getBistroId())) {
            BranchEntity branchEntity = branchMapper.toBranchEntity(branchRequest);
            BistroEntity bistro = bistroService.getBistroEntityById(branchRequest.getBistroId());
            bistro.setBistroId(branchRequest.getBistroId());
            branchEntity.setBistro(bistro);
            branchEntity = branchRepository.save(branchEntity);
            return branchMapper.toBranchResponse(branchEntity);
        } else
            throw new ResourceNotFoundException("Bistro", "Bistro Not found with Id : " + branchRequest.getBistroId());
    }

//    public BranchResponse getBranchById(UUID branchId) throws ResourceNotFoundException {
//        BranchEntity branchEntity = getBranchEntity(branchId);
//        return modelMapper.map(branchEntity, BranchResponse.class);
//    }

    public BranchEntity getBranchEntity(UUID branchId) throws ResourceNotFoundException {
        return branchRepository.findById(branchId).orElseThrow(() -> new ResourceNotFoundException("Branch", "Branch not found with Id" + branchId));
    }

    public BranchEntity getBranchByBranchIDAndBistroId(UUID branchId, UUID bistroId) {
        return branchRepository.findByBranchIdAndBistroId(branchId, bistroId).orElseThrow(
                () -> new ResourceNotFoundException("Bistro's Branch", String.format("Branch not found with branch id %s and bistro id %s", branchId, bistroId))
        );
    }

    public BranchResponse updateBranchByBistroAndBranchId(UUID branchId, UUID bistroId, Map<String, Object> updates) throws ResourceNotFoundException {
        BranchEntity branch = getBranchByBranchIDAndBistroId(branchId, bistroId);
        updates.forEach((key, value) ->
                {
                    Field field = ReflectionUtils.findField(BranchEntity.class, key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(
                            field, branch, value
                    );
                }
        );
        return modelMapper.map(branchRepository.save(branch), BranchResponse.class);
    }
}

