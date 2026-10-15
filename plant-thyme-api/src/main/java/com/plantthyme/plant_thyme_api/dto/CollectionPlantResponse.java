package com.plantthyme.plant_thyme_api.dto;

import com.plantthyme.plant_thyme_api.model.CollectionPlant;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
- Data the API sends back for a plant in the user's collection.
- Includes the catalog plant's info nested under "plant", so the frontend  can keep using collectionPlant.plant.name, collectionPlant.plant.imagePath, etc.
*/

public record CollectionPlantResponse(
        Long id,
        PlantResponse plant,
        LocalDate purchaseDate,
        String purchaseStore,
        BigDecimal cost,
        String nickname,
        String location,
        String notes,
        boolean showNickname,
        boolean showLocation
) {
    // Converts a CollectionPlant entity into a CollectionPlantResponse.
    public static CollectionPlantResponse fromEntity(CollectionPlant collectionPlant) {
        return new CollectionPlantResponse(
                collectionPlant.getId(),
                PlantResponse.fromEntity(collectionPlant.getPlant()),
                collectionPlant.getPurchaseDate(),
                collectionPlant.getPurchaseStore(),
                collectionPlant.getCost(),
                collectionPlant.getNickname(),
                collectionPlant.getLocation(),
                collectionPlant.getNotes(),
                collectionPlant.isShowNickname(),
                collectionPlant.isShowLocation()
        );
    }
}