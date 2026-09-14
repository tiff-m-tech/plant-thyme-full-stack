package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.model.CollectionPlant;
import com.plantthyme.plant_thyme_api.model.ProgressPicture;
import com.plantthyme.plant_thyme_api.repository.CollectionPlantRepository;
import com.plantthyme.plant_thyme_api.repository.ProgressPictureRepository;
import com.plantthyme.plant_thyme_api.service.FileStorageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/progress-pictures")
public class ProgressPictureController {

    private final ProgressPictureRepository progressPictureRepository;
    private final CollectionPlantRepository collectionPlantRepository;
    private final FileStorageService fileStorageService;

    public ProgressPictureController(
            ProgressPictureRepository progressPictureRepository,
            CollectionPlantRepository collectionPlantRepository,
            FileStorageService fileStorageService) {
        this.progressPictureRepository = progressPictureRepository;
        this.collectionPlantRepository = collectionPlantRepository;
        this.fileStorageService = fileStorageService;
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
    // Upload endpoint — takes a FILE (multipart), not JSON. Different request type from my other POST.
    // @RequestParam("file") MultipartFile = the uploaded file. "file" is the name the frontend/Postman labels it.
    // No @RequestBody / @Valid here because there's no JSON body — just a file + a param.
    @PostMapping("/upload")
    public ResponseEntity<ProgressPicture> uploadProgressPicture(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long collectionPlantId) {

        // Find which plant this picture belongs to (bad id -> 400).
        CollectionPlant collectionPlant =
                collectionPlantRepository.findById(collectionPlantId).orElse(null);
        if (collectionPlant == null) {
            return ResponseEntity.badRequest().build();
        }

        // Hand the file to the service; get back the saved filename.
        String filename = fileStorageService.storeFile(file);

        // Build the record with backend-set defaults (user edits type/notes later).
        ProgressPicture picture = new ProgressPicture();
        picture.setCollectionPlant(collectionPlant);
        picture.setImagePath(filename);
        picture.setPictureDate(LocalDate.now());   // backend stamps today's date

        ProgressPicture saved = progressPictureRepository.save(picture);
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