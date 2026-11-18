package com.plantthyme.plant_thyme_api.controller;

import com.plantthyme.plant_thyme_api.dto.ProgressPictureResponse;
import com.plantthyme.plant_thyme_api.dto.ProgressPictureUpdateRequest;
import com.plantthyme.plant_thyme_api.model.CollectionPlant;
import com.plantthyme.plant_thyme_api.model.ProgressPicture;
import com.plantthyme.plant_thyme_api.model.User;
import com.plantthyme.plant_thyme_api.repository.CollectionPlantRepository;
import com.plantthyme.plant_thyme_api.repository.ProgressPictureRepository;
import com.plantthyme.plant_thyme_api.service.FileStorageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // GET the logged-in user's progress pictures — all, or filtered by collection plant
    @GetMapping
    public List<ProgressPictureResponse> getProgressPictures(
            @RequestParam(required = false) Long collectionPlantId,
            @AuthenticationPrincipal User user) {                                                   // NEW

        List<ProgressPicture> pictures = (collectionPlantId != null)
                ? progressPictureRepository.findByCollectionPlantIdAndCollectionPlantUser(collectionPlantId, user)  // CHANGED
                : progressPictureRepository.findByCollectionPlantUser(user);                       // CHANGED

        return pictures.stream()
                .map(ProgressPictureResponse::fromEntity)
                .toList();
    }

    // GET one progress picture by id (only if its plant belongs to the logged-in user)
    @GetMapping("/{id}")
    public ResponseEntity<ProgressPictureResponse> getProgressPictureById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {                                                   // NEW
        return progressPictureRepository.findByIdAndCollectionPlantUser(id, user)                 // CHANGED
                .map(ProgressPictureResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST a new progress picture (only onto the logged-in user's own plant)
    // Upload endpoint — takes a FILE (multipart), not JSON.
    // No @RequestBody / @Valid here because there's no JSON body — just a file + a param.
    @PostMapping("/upload")
    public ResponseEntity<ProgressPictureResponse> uploadProgressPicture(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long collectionPlantId,
            @AuthenticationPrincipal User user) {                                                   // NEW

        CollectionPlant collectionPlant =
                collectionPlantRepository.findByIdAndUser(collectionPlantId, user).orElse(null);  // CHANGED
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

    // PUT update a progress picture's details (only if its plant belongs to the logged-in user)
    @PutMapping("/{id}")
    public ResponseEntity<ProgressPictureResponse> updateProgressPicture(
            @PathVariable Long id,
            @Valid @RequestBody ProgressPictureUpdateRequest request,
            @AuthenticationPrincipal User user) {                                                   // NEW

        ProgressPicture existingPicture =
                progressPictureRepository.findByIdAndCollectionPlantUser(id, user).orElse(null);  // CHANGED

        if (existingPicture == null) {
            return ResponseEntity.notFound().build();
        }

        existingPicture.setPictureDate(request.pictureDate());
        existingPicture.setUpdateType(request.updateType());
        existingPicture.setNotes(request.notes());

        ProgressPicture saved = progressPictureRepository.save(existingPicture);
        return ResponseEntity.ok(ProgressPictureResponse.fromEntity(saved));
    }

    // DELETE a progress picture (only if its plant belongs to the logged-in user)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgressPicture(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {                                                   // NEW

        ProgressPicture existingPicture =
                progressPictureRepository.findByIdAndCollectionPlantUser(id, user).orElse(null);  // CHANGED

        if (existingPicture == null) {
            return ResponseEntity.notFound().build();
        }

        progressPictureRepository.delete(existingPicture);                                         // CHANGED
        return ResponseEntity.noContent().build();
    }
}