package com.bistral.app.bistral_bistro_service.service.bistro_zones.implementaions;

import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import com.bistral.app.bistral_bistro_service.entity.BranchZoneEntity;
import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import com.bistral.app.bistral_bistro_service.repository.ZoneRepository;
import com.bistral.app.bistral_bistro_service.service.BranchService;
import com.bistral.app.bistral_bistro_service.service.bistro_zones.interfaces.IzoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ZoneService implements IzoneService {

    private final ZoneRepository zoneRepository;


    @Override
    public BranchZoneEntity createZone(BranchZoneEntity branchZoneEntity) {

        return zoneRepository.save(branchZoneEntity);
    }


    //TODO to be implemented
    @Override
    public BranchZoneEntity updateZone(BranchZoneEntity branchZoneEntity) {
        return null;
    }

    @Override
    public List<BranchZoneEntity> getAllBranchZones(UUID branchId) {
        return zoneRepository.findAllByBranch(branchId);
    }

    @Override
    public BranchZoneEntity getZone(UUID zoneId) {
        return zoneRepository.findById(zoneId).orElseThrow(() -> new ResourceNotFoundException("Branch zone", 100, "zone Not found"));
    }
}
