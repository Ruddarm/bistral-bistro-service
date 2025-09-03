package com.bistral.app.bistral_bistro_service.controllers;


import com.bistral.app.bistral_bistro_service.dtos.BistroResponse;
import com.bistral.app.bistral_bistro_service.service.BistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bistro")
@RequiredArgsConstructor
public class BistroController {

    private  final BistroService bistroService;

    @GetMapping("/{bistroId}")
    public ResponseEntity<BistroResponse> getBistroById(@PathVariable UUID bistroId){
        return  null;
    }
    @PostMapping("/")
    public ResponseEntity<?> getName() {
        return null;
    }

}
