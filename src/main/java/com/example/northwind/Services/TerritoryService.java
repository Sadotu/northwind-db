package com.example.northwind.Services;

import com.example.northwind.DTO.Input.TerritoryInputDTO;
import com.example.northwind.DTO.Output.TerritoryOutputDTO;
import com.example.northwind.Exceptions.ResourceNotFoundException;
import com.example.northwind.Models.Region;
import com.example.northwind.Models.Territory;
import com.example.northwind.Repositories.RegionRepository;
import com.example.northwind.Repositories.TerritoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TerritoryService {

    private final TerritoryRepository territoryRepository;
    private final RegionRepository regionRepository;

    public TerritoryService(TerritoryRepository territoryRepository, RegionRepository regionRepository) {
        this.territoryRepository = territoryRepository;
        this.regionRepository = regionRepository;
    }

    public TerritoryOutputDTO transferModelToOutputDTO(Territory territory) {
        TerritoryOutputDTO territoryOutputDTO = new TerritoryOutputDTO();
        BeanUtils.copyProperties(territory, territoryOutputDTO);

        return territoryOutputDTO;
    }

    public Territory transferInputDTOToModel(TerritoryInputDTO territoryInputDTO) {
        Territory territory = new Territory();
        BeanUtils.copyProperties(territoryInputDTO, territory);
        return territory;
    }

    public TerritoryOutputDTO addTerritory(TerritoryInputDTO territoryInputDTO) {
        Optional<Territory> existingTerritory = territoryRepository.findByTerritoryDescription(territoryInputDTO.getTerritoryDescription());
        if (existingTerritory.isPresent()) {
            throw new IllegalStateException("A territory with the description '" + territoryInputDTO.getTerritoryDescription() + "' already exists.");
        }

        Territory territory = new Territory();
        territory.setTerritoryId(generateTerritoryId());
        territory.setTerritoryDescription(territoryInputDTO.getTerritoryDescription());

        if (territoryInputDTO.getRegionId() != null) {
            Region region = regionRepository.findById(territoryInputDTO.getRegionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + territoryInputDTO.getRegionId()));
            territory.setRegion(region);
        } else {
            throw new IllegalArgumentException("Region ID must not be null");
        }

        Territory savedTerritory = territoryRepository.save(territory);

        return transferModelToOutputDTO(savedTerritory);
    }

    private String generateTerritoryId() {
        int randomNumber = ThreadLocalRandom.current().nextInt(10000, 100000);

        return String.format("%05d", randomNumber);
    }


    public TerritoryOutputDTO getTerritoryByDescription(String description) {
        Territory territory = territoryRepository.findByTerritoryDescription(description)
                .orElseThrow(() -> new ResourceNotFoundException("Territory not found with description " + description));;

        return transferModelToOutputDTO(territory);
    }

    public TerritoryOutputDTO getTerritoryById(String id) {
        Territory territory = territoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Territory not found with id " + id));

        return transferModelToOutputDTO(territory);
    }
}
