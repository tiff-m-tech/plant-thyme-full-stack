package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.dto.PlantResponse;
import com.plantthyme.plant_thyme_api.repository.PlantRepository;
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
    public List<PlantResponse> getAllPlants() {
        return plantRepository.findAll()
                .stream()
                .map(PlantResponse::fromEntity)
                .toList();
    }

    // GET one plant by id
    @GetMapping("/{id}")
    public ResponseEntity<PlantResponse> getPlantById(@PathVariable Long id) {
        return plantRepository.findById(id)
                .map(PlantResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET search plants by name (e.g. /api/plants/search?name=pothos)
    @GetMapping("/search")
    public List<PlantResponse> searchPlants(@RequestParam String name) {
        return plantRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(PlantResponse::fromEntity)
                .toList();
    }
}