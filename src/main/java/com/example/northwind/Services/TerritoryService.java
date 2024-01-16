package com.example.northwind.Services;

import com.example.northwind.DTO.Input.TerritoryInputDTO;
import com.example.northwind.DTO.Output.TerritoryOutputDTO;
import com.example.northwind.Exceptions.ResourceNotFoundException;
import com.example.northwind.Models.Territory;
import com.example.northwind.Repositories.TerritoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TerritoryService {

    private final TerritoryRepository territoryRepository;

    public TerritoryService(TerritoryRepository territoryRepository) {
        this.territoryRepository = territoryRepository;
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
        Territory territory = territoryRepository.findById(territoryInputDTO.getTerritoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Territory not found with id " + territoryInputDTO.getTerritoryId()));

        return transferModelToOutputDTO(territoryRepository.save(transferInputDTOToModel(territoryInputDTO)));
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
