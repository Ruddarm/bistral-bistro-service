package com.bistral.app.bistral_bistro_service.controllers;


import com.bistral.app.bistral_bistro_service.dtos.TableRequest;
import com.bistral.app.bistral_bistro_service.dtos.TableResponse;
import com.bistral.app.bistral_bistro_service.service.TableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bistros/branch/{branchId}/table")
public class TableController {
    private final TableService tableService;

    @PostMapping
    private List<TableResponse> createTables(@Valid @RequestBody TableRequest tableRequest) {
        return tableService.createTables(tableRequest);
    }

    @GetMapping("/{zoneId}")
    private  List<TableResponse> getTable(@PathVariable UUID branchId, @PathVariable UUID zoneId){
        return tableService.getTables(branchId,zoneId);
    }

}
