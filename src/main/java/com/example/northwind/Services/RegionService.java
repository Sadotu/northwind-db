package com.example.northwind.Services;

import com.example.northwind.DTO.Input.RegionInputDTO;
import com.example.northwind.DTO.Output.RegionOutputDTO;
import com.example.northwind.Exceptions.ResourceNotFoundException;
import com.example.northwind.Models.Region;
import com.example.northwind.Repositories.RegionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
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
}
