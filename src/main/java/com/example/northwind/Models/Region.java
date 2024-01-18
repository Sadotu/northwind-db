package com.example.northwind.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "region")
public class Region {
    @Id
    @Column(name = "region_id", nullable = false)
    private Short regionId;

    @Column(name = "region_description")
    private String regionDescription;
}
