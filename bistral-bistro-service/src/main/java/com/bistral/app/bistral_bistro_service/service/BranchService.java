package com.bistral.app.bistral_bistro_service.service;


import com.bistral.app.bistral_bistro_service.dtos.BranchRequest;
import com.bistral.app.bistral_bistro_service.dtos.BranchResponse;
import com.bistral.app.bistral_bistro_service.entity.BistroEntity;
import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.repository.BistroRepository;
import com.bistral.app.bistral_bistro_service.repository.BranchRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.util.Reflection;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
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
    private final BranchRepository branchRepository;
    private final BistroService bistroService;

    public BranchResponse createBranch(BranchRequest branchRequest) throws ResourceNotFoundException {
        BranchEntity branchEntity = modelMapper.map(branchRequest, BranchEntity.class);
        System.out.println(branchEntity);
        BistroEntity bistro = bistroService.getBistroEntityById(branchRequest.getBistroId());
        branchEntity.setBistro(bistro);
        branchEntity = branchRepository.save(branchEntity);
        return modelMapper.map(branchEntity, BranchResponse.class);
    }

//    public BranchResponse getBranchById(UUID branchId) throws ResourceNotFoundException {
//        BranchEntity branchEntity = getBranchEntity(branchId);
//        return modelMapper.map(branchEntity, BranchResponse.class);
//    }

    public BranchEntity getBranchEntity(UUID branchId) throws ResourceNotFoundException {
        return branchRepository.findById(branchId).orElseThrow(() -> new ResourceNotFoundException("Branch", "Branch not found with Id" + branchId));
    }

    public BranchEntity getBranchByBranchIDAndBistroId(UUID branchId, UUID bistroId) {
        return branchRepository.findByBranchIdAndBistro_bistroId(branchId, bistroId).orElseThrow(
                () -> new ResourceNotFoundException("Bistro's Branch", String.format("Branch not found with branch id %d and bistor id %d", branchId, bistroId))
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

