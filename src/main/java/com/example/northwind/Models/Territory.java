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
@Table(name = "territories")
public class Territory {
    @Id
    @Column(nullable = false, unique = true)
    private String territoryId;
    private String territoryDescription;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region regionId;
}
