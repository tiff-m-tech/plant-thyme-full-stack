package com.plantthyme.plant_thyme_api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@JsonPropertyOrder({"id", "collectionPlant", "imagePath", "pictureDate", "updateType", "notes"})
public class ProgressPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // No @NotNull here — @Valid runs on the request body before the controller
    // sets collectionPlant from the collectionPlantId param, so @NotNull would always
    // fail ("collection plant is required") on a valid request. optional = false enforces it at the DB level instead.
    @ManyToOne(optional = false)
    @JoinColumn(name = "collection_plant_id")
    private CollectionPlant collectionPlant;

    @NotBlank(message = "Image path is required.")
    @Size(max = 255, message = "Image path must be 255 characters or less.")
    private String imagePath;

    @NotNull(message = "Picture date is required.") // When pic added, it auto uses today's date, should be able to change for older pictures
    @PastOrPresent(message = "Picture date cannot be in the future.")
    private LocalDate pictureDate;

    @Pattern( // this is for a dropdown selection, adding validation for postman
            regexp = "Growth|Repotting|Pruning|Pest Treatment|Other",
            message = "Update type must be Growth, Repotting, Pruning, Pest Treatment, or Other."
    )
    private String updateType;

    @Column(length = 1000) // database column
    @Size(max = 1000, message = "Notes must be 1000 characters or less.")
    private String notes;

    public ProgressPicture() {
    }

    public Long getId() {
        return id;
    }

    public CollectionPlant getCollectionPlant() {
        return collectionPlant;
    }

    public void setCollectionPlant(CollectionPlant collectionPlant) {
        this.collectionPlant = collectionPlant;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDate getPictureDate() {
        return pictureDate;
    }

    public void setPictureDate(LocalDate pictureDate) {
        this.pictureDate = pictureDate;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
