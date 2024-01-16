package com.example.northwind.Repositories;

import com.example.northwind.Models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Short> {
    Optional<Region> findById(Short id);
    Optional<Region> findByRegionDescription(String regionDescription);
}