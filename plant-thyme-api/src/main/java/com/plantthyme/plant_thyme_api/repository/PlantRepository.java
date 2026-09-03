package com.plantthyme.plant_thyme_api.repository;

import com.plantthyme.plant_thyme_api.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    // Search plants by name (partial match, case-insensitive)
    List<Plant> findByNameContainingIgnoreCase(String name);
}