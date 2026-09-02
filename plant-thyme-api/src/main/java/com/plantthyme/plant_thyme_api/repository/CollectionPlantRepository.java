package com.plantthyme.plant_thyme_api.repository;

import com.plantthyme.plant_thyme_api.model.CollectionPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionPlantRepository extends JpaRepository<CollectionPlant, Long> {
}