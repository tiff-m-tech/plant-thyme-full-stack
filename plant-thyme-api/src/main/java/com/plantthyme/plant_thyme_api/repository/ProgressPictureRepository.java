package com.plantthyme.plant_thyme_api.repository;

import com.plantthyme.plant_thyme_api.model.ProgressPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressPictureRepository extends JpaRepository<ProgressPicture, Long> {
}