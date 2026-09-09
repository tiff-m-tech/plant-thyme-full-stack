package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.model.CollectionPlant;
import com.plantthyme.plant_thyme_api.model.ProgressPicture;
import com.plantthyme.plant_thyme_api.repository.CollectionPlantRepository;
import com.plantthyme.plant_thyme_api.repository.ProgressPictureRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress-pictures")
public class ProgressPictureController {

    private final ProgressPictureRepository progressPictureRepository;
    private final CollectionPlantRepository collectionPlantRepository;

    public ProgressPictureController(
            ProgressPictureRepository progressPictureRepository,
            CollectionPlantRepository collectionPlantRepository) {
        this.progressPictureRepository = progressPictureRepository;
        this.collectionPlantRepository = collectionPlantRepository;
    }

    // GET all progress pictures
    @GetMapping
    public List<ProgressPicture> getAllProgressPictures() {
        return progressPictureRepository.findAll();
    }

    // GET one progress picture by id
    @GetMapping("/{id}")
    public ResponseEntity<ProgressPicture> getProgressPictureById(@PathVariable Long id) {
        return progressPictureRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST a new progress picture (linked to a collection plant)
    @PostMapping
    public ResponseEntity<ProgressPicture> createProgressPicture(
            @RequestParam Long collectionPlantId,
            @Valid @RequestBody ProgressPicture progressPicture) {

        CollectionPlant collectionPlant =
                collectionPlantRepository.findById(collectionPlantId).orElse(null);

        if (collectionPlant == null) {
            return ResponseEntity.badRequest().build();
        }

        progressPicture.setCollectionPlant(collectionPlant);
        ProgressPicture saved = progressPictureRepository.save(progressPicture);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT update a progress picture (update type and notes, can't update picture)
    @PutMapping("/{id}")
    public ResponseEntity<ProgressPicture> updateProgressPicture(
            @PathVariable Long id,
            @Valid @RequestBody ProgressPicture updatedPicture) {

        ProgressPicture existingPicture =
                progressPictureRepository.findById(id).orElse(null);

        if (existingPicture == null) {
            return ResponseEntity.notFound().build();
        }

        existingPicture.setUpdateType(updatedPicture.getUpdateType());
        existingPicture.setNotes(updatedPicture.getNotes());

        ProgressPicture saved = progressPictureRepository.save(existingPicture);
        return ResponseEntity.ok(saved);
    }

    // DELETE a progress picture
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgressPicture(@PathVariable Long id) {
        if (!progressPictureRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        progressPictureRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}