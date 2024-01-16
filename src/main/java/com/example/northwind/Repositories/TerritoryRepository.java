package com.example.northwind.Repositories;

import com.example.northwind.Models.Territory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerritoryRepository extends JpaRepository<Territory, String> {
    Optional<Territory> findById(String id);
    Optional<Territory> findByTerritoryDescription(String description);
}
