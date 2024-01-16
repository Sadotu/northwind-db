package com.example.northwind.Controllers;

import com.example.northwind.DTO.Input.RegionInputDTO;
import com.example.northwind.DTO.Output.RegionOutputDTO;
import com.example.northwind.Services.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @PostMapping("/add")
    public ResponseEntity<RegionOutputDTO> addRegion(@RequestBody RegionInputDTO regionInputDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(regionService.addRegion(regionInputDTO));
    }

    @GetMapping("/by-description/{region_description}")
    public ResponseEntity<RegionOutputDTO> getRegionByDescription(@PathVariable String region_description) {
        return ResponseEntity.ok(regionService.getRegionByDescription(region_description));
    }

    @GetMapping("/by-id/{region_id}")
    public ResponseEntity<RegionOutputDTO> getRegionById(@PathVariable Short region_id) {
        return ResponseEntity.ok(regionService.getRegionById(region_id));
    }
}
