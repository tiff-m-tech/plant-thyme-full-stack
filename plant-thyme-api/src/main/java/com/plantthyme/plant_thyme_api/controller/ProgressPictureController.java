package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.dto.ProgressPictureResponse;
import com.plantthyme.plant_thyme_api.dto.ProgressPictureUpdateRequest;
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

    // GET progress pictures — all, or filtered by collection plant when the param is present
    @GetMapping
    public List<ProgressPictureResponse> getProgressPictures(
            @RequestParam(required = false) Long collectionPlantId) {

        List<ProgressPicture> pictures = (collectionPlantId != null)
                ? progressPictureRepository.findByCollectionPlantId(collectionPlantId)
                : progressPictureRepository.findAll();

        return pictures.stream()
                .map(ProgressPictureResponse::fromEntity)
                .toList();
    }

    // GET one progress picture by id
    @GetMapping("/{id}")
    public ResponseEntity<ProgressPictureResponse> getProgressPictureById(@PathVariable Long id) {
        return progressPictureRepository.findById(id)
                .map(ProgressPictureResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST a new progress picture
    // Upload endpoint — takes a FILE (multipart), not JSON.
    // No @RequestBody / @Valid here because there's no JSON body — just a file + a param.
    @PostMapping("/upload")
    public ResponseEntity<ProgressPictureResponse> uploadProgressPicture(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long collectionPlantId) {

        CollectionPlant collectionPlant =
                collectionPlantRepository.findById(collectionPlantId).orElse(null);
        if (collectionPlant == null) {
            return ResponseEntity.badRequest().build();
        }

        // Hand the file to the service; get back the saved filename.
        String filename = fileStorageService.storeFile(file);

        ProgressPicture picture = new ProgressPicture();
        picture.setCollectionPlant(collectionPlant);
        picture.setImagePath(filename);
        picture.setPictureDate(LocalDate.now());

        ProgressPicture saved = progressPictureRepository.save(picture);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProgressPictureResponse.fromEntity(saved));
    }

    // PUT update a progress picture's details
    @PutMapping("/{id}")
    public ResponseEntity<ProgressPictureResponse> updateProgressPicture(
            @PathVariable Long id,
            @Valid @RequestBody ProgressPictureUpdateRequest request) {

        ProgressPicture existingPicture =
                progressPictureRepository.findById(id).orElse(null);

        if (existingPicture == null) {
            return ResponseEntity.notFound().build();
        }

        existingPicture.setPictureDate(request.pictureDate());
        existingPicture.setUpdateType(request.updateType());
        existingPicture.setNotes(request.notes());

        ProgressPicture saved = progressPictureRepository.save(existingPicture);
        return ResponseEntity.ok(ProgressPictureResponse.fromEntity(saved));
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