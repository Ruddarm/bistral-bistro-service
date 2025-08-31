package com.bistral.app.bistral_bistro_service.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bistro")
public class BistroController {

    @GetMapping("/")
     public ResponseEntity<?> getName(){
        return  ResponseEntity.ok("oh fuck man");
    }

}
