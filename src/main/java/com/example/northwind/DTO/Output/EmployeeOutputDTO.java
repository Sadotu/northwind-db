package com.example.northwind.DTO.Output;

import com.example.northwind.Models.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class EmployeeOutputDTO {
    private String lastName;
    private String firstName;
    private String title;
    private String titleOfCourtesy;
    private Date birthDate;
    private Date hireDate;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String homePhone;
    private String extension;
    private String notes;
    private String photoPath;
    private boolean active;

    //relations
    private SimpleEmployeeOutputDTO reportsTo;
    private List<TerritoryOutputDTO> territories;
}
