package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.model.CollectionPlant;
import com.plantthyme.plant_thyme_api.model.Plant;
import com.plantthyme.plant_thyme_api.repository.CollectionPlantRepository;
import com.plantthyme.plant_thyme_api.repository.PlantRepository;
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

    // GET all plants in the collection
    @GetMapping
    public List<CollectionPlant> getAllCollectionPlants() {
        return collectionPlantRepository.findAll();
    }

    // GET one collection plant by id
    @GetMapping("/{id}")
    public CollectionPlant getCollectionPlantById(@PathVariable Long id) {
        return collectionPlantRepository.findById(id).orElse(null);
    }

    // POST a plant to the collection
    @PostMapping
    public CollectionPlant createCollectionPlant(
            @RequestParam Long plantId,
            @RequestBody CollectionPlant collectionPlant) {

        Plant plant = plantRepository.findById(plantId).orElse(null);

        if (plant == null) {
            return null;
        }

        collectionPlant.setPlant(plant);

        return collectionPlantRepository.save(collectionPlant);
    }

    // PUT update a collection plant
    @PutMapping("/{id}")
    public CollectionPlant updateCollectionPlant(
            @PathVariable Long id,
            @RequestBody CollectionPlant updatedPlant) {

        CollectionPlant existingPlant =
                collectionPlantRepository.findById(id).orElse(null);

        if (existingPlant == null) {
            return null;
        }

        existingPlant.setPurchaseDate(updatedPlant.getPurchaseDate());
        existingPlant.setPurchaseStore(updatedPlant.getPurchaseStore());
        existingPlant.setCost(updatedPlant.getCost());
        existingPlant.setNickname(updatedPlant.getNickname());
        existingPlant.setLocation(updatedPlant.getLocation());
        existingPlant.setNotes(updatedPlant.getNotes());

        return collectionPlantRepository.save(existingPlant);
    }

    // DELETE a plant from the collection
    @DeleteMapping("/{id}")
    public void deleteCollectionPlant(@PathVariable Long id) {
        collectionPlantRepository.deleteById(id);
    }
}