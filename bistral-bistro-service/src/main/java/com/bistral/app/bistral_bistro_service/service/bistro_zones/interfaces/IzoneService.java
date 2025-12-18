package com.bistral.app.bistral_bistro_service.service.bistro_zones.interfaces;

import com.bistral.app.bistral_bistro_service.entity.BranchZoneEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IzoneService {

    public BranchZoneEntity createZone(BranchZoneEntity branchZoneEntity);

    public BranchZoneEntity updateZone(BranchZoneEntity branchZoneEntity);

    @Query("Select bz from BranchZoneEntity Join fetch TableEntity t where bz.branchId = :branchId ")
    public List<BranchZoneEntity> getAllBranchZones(UUID branchId);

    public BranchZoneEntity getZone(UUID zoneId);
}
