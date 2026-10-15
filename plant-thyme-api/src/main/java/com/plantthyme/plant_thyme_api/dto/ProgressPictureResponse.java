package com.plantthyme.plant_thyme_api.dto;

import com.plantthyme.plant_thyme_api.model.ProgressPicture;

import java.time.LocalDate;

/*
 - Data Transfer Objects (DTOs) define what the API sends and accepts,  separate from the database entities in the model package.
 * DTOs are written as records: compact, immutable data carriers where Java auto-generates the constructor, getters (e.g. name(), not getName()),
 * equals, hashCode, and toString. Entities stay as classes because JPA needs an empty constructor, setters, and the ability to create proxies.
 */

public record ProgressPictureResponse (
        Long id,
        CollectionPlantResponse collectionPlant,
        String imagePath,
        LocalDate pictureDate,
        String updateType,
        String notes
) {
    public static ProgressPictureResponse fromEntity(ProgressPicture progressPicture) {
        return new ProgressPictureResponse(
                progressPicture.getId(),
                CollectionPlantResponse.fromEntity(progressPicture.getCollectionPlant()),
                progressPicture.getImagePath(),
                progressPicture.getPictureDate(),
                progressPicture.getUpdateType(),
                progressPicture.getNotes()
        );
    }
}
