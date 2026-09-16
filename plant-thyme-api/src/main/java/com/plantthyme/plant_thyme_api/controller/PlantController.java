package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.model.Plant;
import com.plantthyme.plant_thyme_api.repository.PlantRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Plant> getPlantById(@PathVariable Long id) {
        return plantRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET search plants by name (e.g. /api/plants/search?name=pothos)
    @GetMapping("/search")
    public List<Plant> searchPlants(@RequestParam String name) {
        return plantRepository.findByNameContainingIgnoreCase(name);
    }
}