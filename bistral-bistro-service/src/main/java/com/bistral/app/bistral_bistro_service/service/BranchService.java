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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BranchService {
    private final ModelMapper modelMapper;
    private final BranchRepository branchRepository;
    private final BistroService bistroService;

    @Transactional
    public boolean createBranch(BranchRequest branchRequest) throws ResourceNotFoundException {
        BranchEntity branchEntity = modelMapper.map(branchRequest, BranchEntity.class);
        BistroEntity bistro = bistroService.getBistroEntityById(branchRequest.getBistroId());
        bistro.getBranches().add(branchEntity);
        return true;
    }

    public BranchResponse getBranchById(UUID branchId) throws ResourceNotFoundException {
        BranchEntity branchEntity = getBranchEntity(branchId);
        return modelMapper.map(branchEntity, BranchResponse.class);
    }

    public BranchEntity getBranchEntity(UUID branchId) throws ResourceNotFoundException {
        return branchRepository.findById(branchId).orElseThrow(() -> new ResourceNotFoundException("Branch", "Branch not found with Id" + branchId));
    }

}

