package com.example.northwind.DTO.Input;

import com.example.northwind.Models.Region;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerritoryInputDTO {
    private String territoryId;
    @Size(max = 60, message = "Address must be less than or equal to 60 characters")
    private String territoryDescription;
    private Region regionId;
}
