package com.example.northwind.DTO.Input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionInputDTO {
    private Short regionId;
    @NotBlank
    @Size(max = 60, message = "Address must be less than or equal to 60 characters")
    private String regionDescription;
}
