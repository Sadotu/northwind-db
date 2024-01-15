package com.example.northwind.Models;

import lombok.*;
import jakarta.persistence.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(nullable = false, unique = true)
    private Short employeeId;
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

    @ManyToOne
    @JoinColumn(name = "reports_to")
    private Employee reportsTo;
}

