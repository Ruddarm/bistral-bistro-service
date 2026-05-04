package com.bistral.app.bistral_bistro_service.controllers;


import com.bistral.app.bistral_bistro_service.dtos.TableResponse;
import com.bistral.app.bistral_bistro_service.dtos.ZoneRequest;
import com.bistral.app.bistral_bistro_service.dtos.ZoneResponse;
import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import com.bistral.app.bistral_bistro_service.entity.BranchZoneEntity;
import com.bistral.app.bistral_bistro_service.service.BranchService;
import com.bistral.app.bistral_bistro_service.service.bistro_zones.interfaces.IzoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("bistros/branch/zone")
public class ZoneController {

    private final IzoneService izoneService;
    private final BranchService branchService;
    private final ModelMapper modelMapper;

    @PostMapping("/")
    public ResponseEntity<ZoneResponse> createZone(@Valid @RequestBody ZoneRequest zoneRequest) {
        BranchEntity branch = branchService.getBranchEntity(zoneRequest.getBranchId());
        BranchZoneEntity branchZoneEntity = modelMapper.map(zoneRequest, BranchZoneEntity.class);
        branchZoneEntity.setBranch(branch);
        return ResponseEntity.ok(
                modelMapper.map(izoneService.createZone(branchZoneEntity), ZoneResponse.class)
        );
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<List<ZoneResponse>> getAllZones(@PathVariable UUID branchId) {
        return ResponseEntity.ok(izoneService.getAllBranchZones(branchId)
                .stream().map((zone) -> {
                    ZoneResponse zoneResponse = modelMapper.map(zone, ZoneResponse.class);
                    List<TableResponse> tableResponses = zone.getTables().stream().map((table) ->
                            modelMapper.map(table, TableResponse.class)
                    ).toList();
                    zoneResponse.setTableResponses(tableResponses);
                    return zoneResponse;
                }).toList()
        );
    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<ZoneResponse> getZones(@PathVariable UUID zoneId) {
        return ResponseEntity.ok(modelMapper.map(izoneService.getZone(zoneId), ZoneResponse.class));
    }
}
