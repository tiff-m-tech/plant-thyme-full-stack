package com.plantthyme.plant_thyme_api.repository;

import com.plantthyme.plant_thyme_api.model.ProgressPicture;
import com.plantthyme.plant_thyme_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressPictureRepository extends JpaRepository<ProgressPicture, Long> {

    // All pictures for one plant, but only if that plant belongs to this user
    List<ProgressPicture> findByCollectionPlantIdAndCollectionPlantUser(Long collectionPlantId, User user);

    // All pictures across all of this user's plants
    List<ProgressPicture> findByCollectionPlantUser(User user);

    // One picture, but only if its plant belongs to this user
    Optional<ProgressPicture> findByIdAndCollectionPlantUser(Long id, User user);
}