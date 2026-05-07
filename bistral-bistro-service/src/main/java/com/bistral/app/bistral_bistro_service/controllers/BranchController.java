package com.bistral.app.bistral_bistro_service.controllers;

import com.bistral.app.bistral_bistro_service.dtos.ApiResponse;
import com.bistral.app.bistral_bistro_service.dtos.BranchRequest;
import com.bistral.app.bistral_bistro_service.dtos.BranchResponse;
import com.bistral.app.bistral_bistro_service.dtos.ZoneResponse;
import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import com.bistral.app.bistral_bistro_service.mapperInterface.BranchMapper;
import com.bistral.app.bistral_bistro_service.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/bistros/branches")
@RequiredArgsConstructor
public class BranchController {

    private final ModelMapper modelMapper;
    private final BranchMapper branchMapper;
    private final BranchService branchService;

    /*
        Create branch
     */
    @PostMapping
    public ResponseEntity<BranchResponse> createBranch(@Valid @RequestBody BranchRequest branchRequest) {
        return ResponseEntity.ok(branchService.createBranch(branchRequest));
    }


    @GetMapping("")
    public ResponseEntity<BranchResponse> getBranchByIdAndBistroId() {
        BranchEntity branchEntity = branchService.getBranchByBranchIDAndBistroId();
        BranchResponse response = branchMapper.toBranchResponse(branchEntity);
//        List<ZoneResponse>  zoneResponses = branchEntity.getZones()
//                .stream().map((zone)->modelMapper.map(zone
//                ,ZoneResponse.class)).toList();
//        response.setZoneResponses(zoneResponses);
//
        return ResponseEntity.ok(response);
    }

//    TODO : Need TO Fix
    @PatchMapping("")
    public ResponseEntity<?> updateBranch(
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(branchService.updateBranchByBistroAndBranchId(updates));
    }


    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<BranchResponse>>> getBranchListByBistroId(){
        return null;
    }
}
