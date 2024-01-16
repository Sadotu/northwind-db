package com.example.northwind.DTO.Output;

import com.example.northwind.Models.Region;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerritoryOutputDTO {
    private String territoryDescription;
    private Region regionId;
}
