package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.model.CollectionPlant;
import com.plantthyme.plant_thyme_api.model.ProgressPicture;
import com.plantthyme.plant_thyme_api.repository.CollectionPlantRepository;
import com.plantthyme.plant_thyme_api.repository.ProgressPictureRepository;
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
    public ProgressPicture getProgressPictureById(@PathVariable Long id) {
        return progressPictureRepository.findById(id).orElse(null);
    }

    // POST a new progress picture (linked to a collection plant)
    @PostMapping
    public ProgressPicture createProgressPicture(
            @RequestParam Long collectionPlantId,
            @RequestBody ProgressPicture progressPicture) {

        CollectionPlant collectionPlant =
                collectionPlantRepository.findById(collectionPlantId).orElse(null);

        if (collectionPlant == null) {
            return null;
        }

        progressPicture.setCollectionPlant(collectionPlant);

        return progressPictureRepository.save(progressPicture);
    }

    // PUT update a progress picture (update type and notes, can't update picture)
    @PutMapping("/{id}")
    public ProgressPicture updateProgressPicture(
            @PathVariable Long id,
            @RequestBody ProgressPicture updatedPicture) {

        ProgressPicture existingPicture =
                progressPictureRepository.findById(id).orElse(null);

        if (existingPicture == null) {
            return null;
        }

        existingPicture.setUpdateType(updatedPicture.getUpdateType());
        existingPicture.setNotes(updatedPicture.getNotes());

        return progressPictureRepository.save(existingPicture);
    }

    // DELETE a progress picture
    @DeleteMapping("/{id}")
    public void deleteProgressPicture(@PathVariable Long id) {
        progressPictureRepository.deleteById(id);
    }
}