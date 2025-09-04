package com.bistral.app.bistral_bistro_service.service;

import com.bistral.app.bistral_bistro_service.contexts.UserContext;
import com.bistral.app.bistral_bistro_service.dtos.BistroRequest;
import com.bistral.app.bistral_bistro_service.dtos.BistroResponse;
import com.bistral.app.bistral_bistro_service.dtos.BranchResponse;
import com.bistral.app.bistral_bistro_service.entity.BistroEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
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

    private final ModelMapper modelMapper;
    private final BistroRepository bistroRepository;

    public BistroResponse createBistro(BistroRequest bistroRequest) {
        BistroEntity bistroEntity = modelMapper.map(bistroRequest, BistroEntity.class);
        bistroRepository.save(bistroEntity);
        return modelMapper.map(bistroEntity, BistroResponse.class);
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
                .orElseThrow(() -> new ResourceNotFoundException("Bistro", "Bistro Not Found Exception"));
    }

    public List<BranchResponse> getListOfBranches(UUID bistroId) throws ResourceNotFoundException {
        BistroEntity bistroEntity = getBistroEntityById(bistroId);
        return bistroEntity.getBranches()
                .stream()
                .map((branch) -> modelMapper.map(branch, BranchResponse.class))
                .collect(Collectors.toList());
    }

}
