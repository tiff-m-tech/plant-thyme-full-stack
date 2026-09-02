package com.plantthyme.plant_thyme_api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@JsonPropertyOrder({"id", "collectionPlant", "imagePath", "pictureDate", "updateType", "notes"})
public class ProgressPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "collection_plant_id")
    private CollectionPlant collectionPlant;

    private String imagePath;
    private LocalDate pictureDate;
    private String updateType;
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
