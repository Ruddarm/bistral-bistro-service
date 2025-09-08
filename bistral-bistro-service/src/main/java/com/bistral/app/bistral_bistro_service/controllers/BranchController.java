package com.bistral.app.bistral_bistro_service.controllers;

import com.bistral.app.bistral_bistro_service.dtos.BranchRequest;
import com.bistral.app.bistral_bistro_service.dtos.BranchResponse;
import com.bistral.app.bistral_bistro_service.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/bistros/{bistroId}/branches")
@RequiredArgsConstructor
public class BranchController {

    public final ModelMapper modelMapper;
    public final BranchService branchService;

    /*
        Crerate branch
     */
    @PostMapping
    public ResponseEntity<BranchResponse> createBranch(@Valid @RequestBody BranchRequest branchRequest) {
        return ResponseEntity.ok(branchService.createBranch(branchRequest));
    }



    @GetMapping("/{branchId}")
    public ResponseEntity<BranchResponse> getBranchByIdAndBistroId(@PathVariable UUID bistroId, @PathVariable UUID branchId) {
        return ResponseEntity.ok(modelMapper.map(branchService.getBranchByBranchIDAndBistroId(branchId, bistroId), BranchResponse.class));
    }

    @PatchMapping("/{branchId}")
    public ResponseEntity<?> updateBranch(@PathVariable UUID bistroId, @PathVariable UUID branchId,
                                          @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(branchService.updateBranchByBistroAndBranchId(branchId,bistroId,updates));
    }
}
