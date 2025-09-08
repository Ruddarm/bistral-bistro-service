package com.bistral.app.bistral_bistro_service.controllers;


import com.bistral.app.bistral_bistro_service.dtos.BistroRequest;
import com.bistral.app.bistral_bistro_service.dtos.BistroResponse;
import com.bistral.app.bistral_bistro_service.dtos.BranchResponse;
import com.bistral.app.bistral_bistro_service.dtos.MenuResponse;
import com.bistral.app.bistral_bistro_service.service.BistroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bistros")
@RequiredArgsConstructor
public class BistroController {

    private final BistroService bistroService;
    private final ModelMapper modelMapper;

    @GetMapping("/{bistroId}")
    public ResponseEntity<BistroResponse> getBistroById(@PathVariable UUID bistroId) {
        return ResponseEntity.ok(modelMapper.map(bistroService.getBistroEntityById(bistroId), BistroResponse.class));
    }

    /*
        Create Bistro
        @param Bistrorequest
     */
    @PostMapping
    public ResponseEntity<BistroResponse> createBistro(@Valid @RequestBody BistroRequest bistroRequest) {
        return ResponseEntity.ok(bistroService.createBistro(bistroRequest));
    }

    @GetMapping("/list/branch/{bistroId}")
    public ResponseEntity<List<BranchResponse>> getAllBistros(@PathVariable UUID bistroId) {
        return ResponseEntity.ok(bistroService.getListOfBranches(bistroId));
    }

    @GetMapping("/list/menus/{bistroId}")
    public ResponseEntity<List<MenuResponse>> getAllMenus(@PathVariable UUID bistroId) {
        return ResponseEntity.ok(bistroService.getListOfMenus(bistroId));
    }
}
