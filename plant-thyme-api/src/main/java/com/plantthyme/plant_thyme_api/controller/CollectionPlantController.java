package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.model.CollectionPlant;
import com.plantthyme.plant_thyme_api.model.Plant;
import com.plantthyme.plant_thyme_api.repository.CollectionPlantRepository;
import com.plantthyme.plant_thyme_api.repository.PlantRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collection-plants")
public class CollectionPlantController {

    private final CollectionPlantRepository collectionPlantRepository;
    private final PlantRepository plantRepository;

    public CollectionPlantController(
            CollectionPlantRepository collectionPlantRepository,
            PlantRepository plantRepository) {
        this.collectionPlantRepository = collectionPlantRepository;
        this.plantRepository = plantRepository;
    }

    // GET all collection plants
    @GetMapping
    public List<CollectionPlant> getAllCollectionPlants() {
        return collectionPlantRepository.findAll();
    }

    // GET one collection plant by id
    @GetMapping("/{id}")
    public ResponseEntity<CollectionPlant> getCollectionPlantById(@PathVariable Long id) {

        return collectionPlantRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST add new collection plant
    @PostMapping
    public ResponseEntity<CollectionPlant> createCollectionPlant(
            @RequestParam Long plantId,
            @Valid @RequestBody CollectionPlant collectionPlant) {

        Plant plant = plantRepository.findById(plantId).orElse(null);

        if (plant == null) {
            return ResponseEntity.badRequest().build();
        }

        collectionPlant.setPlant(plant);
        CollectionPlant saved = collectionPlantRepository.save(collectionPlant);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT update collection plant
    @PutMapping("/{id}")
    public ResponseEntity<CollectionPlant> updateCollectionPlant(
            @PathVariable Long id,
            @Valid @RequestBody CollectionPlant updatedPlant) {

        CollectionPlant existingPlant =
                collectionPlantRepository.findById(id).orElse(null);

        if (existingPlant == null) {
            return ResponseEntity.notFound().build();
        }

        existingPlant.setPurchaseDate(updatedPlant.getPurchaseDate());
        existingPlant.setPurchaseStore(updatedPlant.getPurchaseStore());
        existingPlant.setCost(updatedPlant.getCost());
        existingPlant.setNickname(updatedPlant.getNickname());
        existingPlant.setLocation(updatedPlant.getLocation());
        existingPlant.setNotes(updatedPlant.getNotes());
        existingPlant.setShowNickname(updatedPlant.isShowNickname());
        existingPlant.setShowLocation(updatedPlant.isShowLocation());

        CollectionPlant saved = collectionPlantRepository.save(existingPlant);
        return ResponseEntity.ok(saved);
    }

    // DELETE one collection plant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectionPlant(@PathVariable Long id) {
        if (!collectionPlantRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        collectionPlantRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}