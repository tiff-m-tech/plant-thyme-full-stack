package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.model.Plant;
import com.plantthyme.plant_thyme_api.repository.PlantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantRepository plantRepository;

    public PlantController(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    // GET all plants
    @GetMapping
    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    // POST a new plant (for seeding data)
    @PostMapping
    public Plant createPlant(@RequestBody Plant plant) {
        return plantRepository.save(plant);
    }
}