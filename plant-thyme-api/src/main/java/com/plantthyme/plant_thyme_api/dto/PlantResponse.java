package com.plantthyme.plant_thyme_api.dto;

import com.plantthyme.plant_thyme_api.model.Plant;

// Data the API sends back for a catalog plant.

public record PlantResponse(
        Long id,
        String name,
        String imagePath,
        String light,
        String water,
        String fertilize
) {

    // Converts a Plant entity into a PlantResponse.
    public static PlantResponse fromEntity(Plant plant) {
        return new PlantResponse(
                plant.getId(),
                plant.getName(),
                plant.getImagePath(),
                plant.getLight(),
                plant.getWater(),
                plant.getFertilize()
        );
    }
}