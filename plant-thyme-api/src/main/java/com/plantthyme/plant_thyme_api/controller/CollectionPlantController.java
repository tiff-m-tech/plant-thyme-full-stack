package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.dto.CollectionPlantRequest;
import com.plantthyme.plant_thyme_api.dto.CollectionPlantResponse;
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
    public List<CollectionPlantResponse> getAllCollectionPlants() {
        return collectionPlantRepository.findAll()
                .stream()
                .map(CollectionPlantResponse::fromEntity)
                .toList();
    }

    // GET one collection plant by id
    @GetMapping("/{id}")
    public ResponseEntity<CollectionPlantResponse> getCollectionPlantById(@PathVariable Long id) {
        return collectionPlantRepository.findById(id)
                .map(CollectionPlantResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST add new collection plant
    @PostMapping
    public ResponseEntity<CollectionPlantResponse> createCollectionPlant(
            @RequestParam Long plantId,
            @Valid @RequestBody CollectionPlantRequest request) {

        Plant plant = plantRepository.findById(plantId).orElse(null);

        if (plant == null) {
            return ResponseEntity.badRequest().build();
        }

        CollectionPlant collectionPlant = new CollectionPlant();
        collectionPlant.setPlant(plant);
        applyRequest(collectionPlant, request);

        CollectionPlant saved = collectionPlantRepository.save(collectionPlant);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CollectionPlantResponse.fromEntity(saved));
    }

    // PUT update collection plant
    @PutMapping("/{id}")
    public ResponseEntity<CollectionPlantResponse> updateCollectionPlant(
            @PathVariable Long id,
            @Valid @RequestBody CollectionPlantRequest request) {

        CollectionPlant existingPlant = collectionPlantRepository.findById(id).orElse(null);

        if (existingPlant == null) {
            return ResponseEntity.notFound().build();
        }

        applyRequest(existingPlant, request);

        CollectionPlant saved = collectionPlantRepository.save(existingPlant);
        return ResponseEntity.ok(CollectionPlantResponse.fromEntity(saved));
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

    // Copies the user-editable fields from the request onto the entity.
    // Used by both POST and PUT so the field list lives in one place.
    private void applyRequest(CollectionPlant collectionPlant, CollectionPlantRequest request) {
        collectionPlant.setPurchaseDate(request.purchaseDate());
        collectionPlant.setPurchaseStore(request.purchaseStore());
        collectionPlant.setCost(request.cost());
        collectionPlant.setNickname(request.nickname());
        collectionPlant.setLocation(request.location());
        collectionPlant.setNotes(request.notes());
        collectionPlant.setShowNickname(Boolean.TRUE.equals(request.showNickname()));
        collectionPlant.setShowLocation(Boolean.TRUE.equals(request.showLocation()));
    }
}