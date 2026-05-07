package com.bistral.app.bistral_bistro_service.controllers;


import com.bistral.app.bistral_bistro_service.dtos.*;
import com.bistral.app.bistral_bistro_service.mapperInterface.BistroMapper;
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
    private final BistroMapper bistroMapper;


    @GetMapping()
    public ResponseEntity<BistroResponse> getBistroById() {
        return ResponseEntity.ok(bistroService.getBistroResponseByBistroID());
    }


    /*
        Create Bistro
        @param Bistrorequest
     */
    @PostMapping
    public ResponseEntity<BistroResponse> createBistro(@Valid @RequestBody BistroRequest bistroRequest) {
        return ResponseEntity.ok(bistroService.createBistro(bistroRequest));
    }


    @GetMapping("/list/branch/")
    public ResponseEntity<List<BranchResponse>> getAllBistros() {
        return ResponseEntity.ok(bistroService.getListOfBranches());
    }


    @GetMapping("/list/bistros/user/{userId}")
    public ResponseEntity<List<BistroResponse>> getAllBistrosOfUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(bistroService.getAllBistroOfUser(userId));
    }


    @Deprecated
    @GetMapping("/list/bistros/menus/{userId}")
    public ResponseEntity<List<BistroWithMenus>> getAllBistroWithMenus(@PathVariable UUID userId) {
        return ResponseEntity.ok(bistroService.getListOfBistroWithMenus(userId));
    }


    @PostMapping("/internal/context")
    public ResponseEntity<List<BistroContextDto>> getBistroContext(@RequestBody BistroContextRequestDto bistroContextRequestDto) {
        return ResponseEntity.ok(bistroService.getListOfBistroContext(bistroContextRequestDto.getBistroIds(),
                bistroContextRequestDto.getBranchIds()));
    }

}
