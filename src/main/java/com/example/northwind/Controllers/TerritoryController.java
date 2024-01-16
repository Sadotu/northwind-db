package com.example.northwind.Controllers;

import com.example.northwind.DTO.Input.TerritoryInputDTO;
import com.example.northwind.DTO.Output.TerritoryOutputDTO;
import com.example.northwind.Services.TerritoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/territories")
public class TerritoryController {

    private final TerritoryService territoryService;

    public TerritoryController(TerritoryService territoryService) {
        this.territoryService = territoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<TerritoryOutputDTO> addTerritory(@RequestBody TerritoryInputDTO territoryInputDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(territoryService.addTerritory(territoryInputDTO));
    }

    @GetMapping("/by-description/{territory_description}")
    public ResponseEntity<TerritoryOutputDTO> getTerritoryByDescription(@PathVariable String territory_description) {
        return ResponseEntity.ok(territoryService.getTerritoryByDescription(territory_description));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<TerritoryOutputDTO> getTerritoryById(@PathVariable String id) {
        return ResponseEntity.ok(territoryService.getTerritoryById(id));
    }
}
