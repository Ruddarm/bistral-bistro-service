package com.bistral.app.bistral_bistro_service.service.bistro_zones.interfaces;

import com.bistral.app.bistral_bistro_service.dtos.ZoneRequest;
import com.bistral.app.bistral_bistro_service.entity.BranchZoneEntity;

import java.util.List;
import java.util.UUID;

public interface ZoneService {

    public BranchZoneEntity createZone(ZoneRequest zoneRequest);

    public BranchZoneEntity updateZone(BranchZoneEntity branchZoneEntity);

    public List<BranchZoneEntity> getAllBranchZones();

    public BranchZoneEntity getZone(UUID zoneId);
}
