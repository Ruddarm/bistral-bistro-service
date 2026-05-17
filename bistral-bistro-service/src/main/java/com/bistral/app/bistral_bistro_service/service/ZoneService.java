package com.bistral.app.bistral_bistro_service.service;

import com.bistral.app.bistral_bistro_service.contexts.UserContextHolder;
import com.bistral.app.bistral_bistro_service.dtos.ZoneRequest;
import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import com.bistral.app.bistral_bistro_service.entity.BranchZoneEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ZoneService  {

    private final ZoneRepository zoneRepository;
    private final BranchService branchService;

    public BranchZoneEntity createZone(ZoneRequest zoneRequest) {
        return zoneRepository.save(BranchZoneEntity
                .builder()
                .createdBy(UserContextHolder.getAuthContext().getUserId())
                .branch(
                        BranchEntity.builder()
                                .branchId(UserContextHolder
                                        .getAuthContext()
                                        .getBranchId()).build())
                .zoneName(zoneRequest.getZoneName())
                .build());
    }


    public BranchZoneEntity updateZone(BranchZoneEntity branchZoneEntity) {
        return null;
    }

    public List<BranchZoneEntity> getAllBranchZones() {
        return zoneRepository.findAllByBranch(UserContextHolder
                .getAuthContext()
                .getBranchId());
    }

    public BranchZoneEntity getZone(UUID zoneId) {
        return zoneRepository.findByZoneIdAndBranch_BranchId(zoneId,
                UserContextHolder.getAuthContext().getBranchId()
        ).orElseThrow(() -> new ResourceNotFoundException("Branch zone", 100, "zone Not found"));
    }
}
