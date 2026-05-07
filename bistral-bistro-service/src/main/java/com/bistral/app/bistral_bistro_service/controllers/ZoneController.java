package com.bistral.app.bistral_bistro_service.controllers;


import com.bistral.app.bistral_bistro_service.dtos.TableResponse;
import com.bistral.app.bistral_bistro_service.dtos.ZoneRequest;
import com.bistral.app.bistral_bistro_service.dtos.ZoneResponse;
import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import com.bistral.app.bistral_bistro_service.entity.BranchZoneEntity;
import com.bistral.app.bistral_bistro_service.service.BranchService;
import com.bistral.app.bistral_bistro_service.service.bistro_zones.interfaces.ZoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("bistros/branches/zone")
public class ZoneController {

    private final ZoneService zoneService;
    private final BranchService branchService;
    private final ModelMapper modelMapper;

    @PostMapping("/")
    public ResponseEntity<ZoneResponse> createZone(@Valid @RequestBody ZoneRequest zoneRequest) {
        return ResponseEntity.ok(
                modelMapper.map(zoneService.createZone(zoneRequest), ZoneResponse.class)
        );
    }

    @GetMapping("/")
    public ResponseEntity<List<ZoneResponse>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllBranchZones()
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
        return ResponseEntity.ok(modelMapper.map(zoneService.getZone(zoneId), ZoneResponse.class));
    }
}
