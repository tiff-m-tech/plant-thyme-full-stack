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

    // GET one plant by id
    @GetMapping("/{id}")
    public Plant getPlantById(@PathVariable Long id) {
        return plantRepository.findById(id).orElse(null);
    }

    // GET search plants by name (e.g. /api/plants/search?name=pothos)
    @GetMapping("/search")
    public List<Plant> searchPlants(@RequestParam String name) {
        return plantRepository.findByNameContainingIgnoreCase(name);
    }

    // POST a new plant
    @PostMapping
    public Plant createPlant(@RequestBody Plant plant) {
        return plantRepository.save(plant);
    }
}