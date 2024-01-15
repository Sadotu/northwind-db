package com.example.northwind.DTO.Input;

import com.example.northwind.Models.Employee;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeInputDTO {

    private Short employeeId;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 20, message = "Last name must be less than or equal to 20 characters")
    private String lastName;

    @NotBlank(message = "First name is mandatory")
    @Size(max = 10, message = "First name must be less than or equal to 10 characters")
    private String firstName;

    @Size(max = 30, message = "Title must be less than or equal to 30 characters")
    private String title;

    @Size(max = 5, message = "Title must be less than or equal to 5 characters")
    private String titleOfCourtesy;

    @Past(message = "Date of birth must be a date in the past")
    private Date birthDate;

    @PastOrPresent(message = "Hire date must be a date in the past or the present")
    private Date hireDate;

    @Size(max = 60, message = "Address must be less than or equal to 60 characters")
    private String address;

    @Size(max = 15, message = "City must be less than or equal to 15 characters")
    private String city;

    @Size(max = 15, message = "Region must be less than or equal to 15 characters")
    private String region;

    @Pattern(regexp = "^[A-Za-z0-9]{1,10}$", message = "Postal code must be alphanumeric and less than or equal to 10 characters")
    private String postalCode;

    @Size(max = 15, message = "Country must be less than or equal to 15 characters")
    private String country;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Home phone must be a valid phone number")
    private String homePhone;

    @Size(max = 4, message = "Extension must be less than or equal to 4 characters")
    private String extension;

    @Size(max = 400, message = "Note must be less than or equal to 400 characters")
    private String notes;
    private String photoPath;
    private boolean active;

    //relations
    private Employee reportsTo;
}
