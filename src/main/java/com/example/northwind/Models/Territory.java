package com.example.northwind.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "territories")
public class Territory {
    @Id
    @Column(nullable = false, unique = true)
    private Short territoryId;
    private String territoryDescription;
    private Region regionId;
}
