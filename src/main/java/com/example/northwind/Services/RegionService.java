package com.example.northwind.Services;

import com.example.northwind.DTO.Input.RegionInputDTO;
import com.example.northwind.DTO.Output.RegionOutputDTO;
import com.example.northwind.DTO.Output.TerritoryOutputDTO;
import com.example.northwind.Exceptions.CannotDeleteException;
import com.example.northwind.Exceptions.ResourceNotFoundException;
import com.example.northwind.Models.Region;
import com.example.northwind.Repositories.RegionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService {

    private final RegionRepository regionRepository;
    private final TerritoryService territoryService;

    public RegionService(RegionRepository regionRepository, TerritoryService territoryService) {
        this.regionRepository = regionRepository;
        this.territoryService = territoryService;
    }

    public RegionOutputDTO transferModelToOutputDTO(Region region) {
        RegionOutputDTO regionOutputDTO = new RegionOutputDTO();
        BeanUtils.copyProperties(region, regionOutputDTO);

        return regionOutputDTO;
    }

    public Region transferInputDTOToModel(RegionInputDTO regionInputDTO) {
        Region region = new Region();
        BeanUtils.copyProperties(regionInputDTO, region);
        return region;
    }

    public RegionOutputDTO addRegion(RegionInputDTO regionInputDTO) {
        Region latestRegion = regionRepository.findTopByOrderByRegionIdDesc()
                .orElse(null);

        Short newRegionId = (short) (latestRegion != null ? (latestRegion.getRegionId() + 1) : 1);

        Region region = new Region();
        region.setRegionId(newRegionId);
        region.setRegionDescription(regionInputDTO.getRegionDescription());

        region = regionRepository.save(region);

        return transferModelToOutputDTO(region);
    }


    public RegionOutputDTO getRegionByDescription(String name) {
        Region region = regionRepository.findByRegionDescription(name)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with name " + name));;

        return transferModelToOutputDTO(region);
    }

    public RegionOutputDTO getRegionById(Short id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id));

        return transferModelToOutputDTO(region);
    }

    public RegionOutputDTO updateRegionById(Short id, RegionInputDTO regionInputDTO) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id));

        String newDescription = regionInputDTO.getRegionDescription();
        region.setRegionDescription(newDescription);

        return transferModelToOutputDTO(regionRepository.save(region));
    }

    @Transactional
    public void deleteRegion(Short regionId) throws CannotDeleteException {
        List<TerritoryOutputDTO> territories = territoryService.getTerritoriesByRegion(regionId);

        if (!territories.isEmpty()) {
            String territoryDetails = territories.stream()
                    .map(t -> t.getTerritoryId() + " - " + t.getTerritoryDescription())
                    .collect(Collectors.joining(", "));

            throw new CannotDeleteException("Cannot delete region as it still contains territories: " + territoryDetails);
        }

        regionRepository.deleteById(regionId);
    }
}
