package com.plantthyme.plant_thyme_api.repository;

import com.plantthyme.plant_thyme_api.model.CollectionPlant;
import com.plantthyme.plant_thyme_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionPlantRepository extends JpaRepository<CollectionPlant, Long> {
    // All plants belonging to one user
    List<CollectionPlant> findByUser(User user);

    // One plant, but only if it belongs to this user
    Optional<CollectionPlant> findByIdAndUser(Long id, User user);
}