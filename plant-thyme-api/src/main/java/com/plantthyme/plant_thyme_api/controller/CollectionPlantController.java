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

@RestController // marks this class as a REST controller, each method returns data (JSON), not a webpage. Combines @Controller + @ResponseBody.
@RequestMapping("/api/collection-plants") // base URL for every endpoint in this class. So a method's @GetMapping("/{id}") becomes /api/collection-plants/{id}
public class CollectionPlantController {

// ResponseEntity is an object that represents the entire HTTP response, status code, headers, and body, all in one. I'm taking full control of what gets sent back, instead of letting Spring guess.

// Without ResponseEntity — I return the data directly:
//  public CollectionPlant getOne(...) {
//      return someCollectionPlant;   -->  Spring wraps this: always 200 OK, body = the object, even with empty body
//  }
//
//  With ResponseEntity — I return the data AND the status:
//  public ResponseEntity<CollectionPlant> getOne(...) {
//      return ResponseEntity.ok(someCollectionPlant); --> 200 status + body
//       or
//      return ResponseEntity.notFound().build();   --> 404 status + no body
//  }

    // these two repos are the "how to store/fetch" layer. The controller calls them; it never touches the DB directly.
    private final CollectionPlantRepository collectionPlantRepository;
    private final PlantRepository plantRepository;

    // constructor injection. Spring automatically passes in the repositories when it creates this controller.
    // I need BOTH repos here — collectionPlant to save/read collection plants, plant to look up the parent Plant by id.
    public CollectionPlantController(
            CollectionPlantRepository collectionPlantRepository,
            PlantRepository plantRepository) {
        this.collectionPlantRepository = collectionPlantRepository;
        this.plantRepository = plantRepository;
    }

    // GET all — no error handling needed. Empty list is a valid result, not an error.
    @GetMapping
    public List<CollectionPlant> getAllCollectionPlants() {
        return collectionPlantRepository.findAll();
    }

    // GET one by id. Returns ResponseEntity so I can send different HTTP statuses (200 vs 404).
    @GetMapping("/{id}")
    public ResponseEntity<CollectionPlant> getCollectionPlantById(@PathVariable Long id) {
        // findById returns an Optional (a box that's either "has a value" or "empty").
        // .map(...) -> if found, wrap it in a 200 OK. .orElse(...) -> if empty, send 404 Not Found.
        // this is the fix for the "return null" bug, null was sending 200 + empty body
        return collectionPlantRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST = create. @RequestParam pulls plantId from the URL query (?plantId=7). @RequestBody is the JSON.
    // @Valid is the TRIGGER — it tells Spring to run Bean Validation on the body BEFORE this method's code runs.
    // Without @Valid, all the @Size/@PositiveOrZero annotations on the entity do nothing.
    @PostMapping
    public ResponseEntity<CollectionPlant> createCollectionPlant(
            @RequestParam Long plantId,
            @Valid @RequestBody CollectionPlant collectionPlant) {

        // look up the parent Plant. If the plantId is bad, this Optional is empty.
        Plant plant = plantRepository.findById(plantId).orElse(null);

        // error handling — bad plantId -> 400 Bad Request. Stops here, never saves an non existing collection plant.
        if (plant == null) {
            return ResponseEntity.badRequest().build();
        }

        // attach the found plant. This is WHY the entity has no @NotNull on plant — it's set here, after validation.
        collectionPlant.setPlant(plant);
        CollectionPlant saved = collectionPlantRepository.save(collectionPlant);

        // 201 Created for a successful POST (vs plain 200).
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT = update existing. id from the path says WHICH one; body has the new values.
    @PutMapping("/{id}")
    public ResponseEntity<CollectionPlant> updateCollectionPlant(
            @PathVariable Long id,
            @Valid @RequestBody CollectionPlant updatedPlant) {

        // load the existing record first. Can't update what isn't there.
        CollectionPlant existingPlant =
                collectionPlantRepository.findById(id).orElse(null);

        // error handling — updating a nonexistent id -> 404.
        if (existingPlant == null) {
            return ResponseEntity.notFound().build();
        }

        // copy fields ONE BY ONE from the incoming body onto the existing record.
        // I DON'T copy id or plant, so the client can't overwrite them. Only the editable fields change.
        existingPlant.setPurchaseDate(updatedPlant.getPurchaseDate());
        existingPlant.setPurchaseStore(updatedPlant.getPurchaseStore());
        existingPlant.setCost(updatedPlant.getCost());
        existingPlant.setNickname(updatedPlant.getNickname());
        existingPlant.setLocation(updatedPlant.getLocation());
        existingPlant.setNotes(updatedPlant.getNotes());

        // save the modified existing record. Because it has an id, JPA does an UPDATE, not an INSERT.
        CollectionPlant saved = collectionPlantRepository.save(existingPlant);
        return ResponseEntity.ok(saved);
    }

    // DELETE by id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectionPlant(@PathVariable Long id) {
        // check it exists first — deleteById silently does nothing on a missing id, so without this I'd return 200
        if (!collectionPlantRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        collectionPlantRepository.deleteById(id);
        // 204 No Content = "success, nothing to send back". Standard for delete.
        return ResponseEntity.noContent().build();
    }
}