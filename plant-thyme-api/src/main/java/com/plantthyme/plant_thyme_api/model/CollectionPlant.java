package com.plantthyme.plant_thyme_api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonPropertyOrder({"id", "plant", "purchaseDate", "purchaseStore", "cost", "nickname", "location", "notes"})
public class CollectionPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    // cascade = ALL + orphanRemoval delete a plant's progress pictures when the plant is deleted
    @OneToMany(
            mappedBy = "collectionPlant",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<ProgressPicture> progressPictures = new ArrayList<>();

    @PastOrPresent(message = "Purchase date cannot be in the future.")
    private LocalDate purchaseDate;

    @Size(max = 50, message = "Purchase store must be 50 characters or less.")
    private String purchaseStore;

    @PositiveOrZero(message = "Cost cannot be negative.")
    @Column(precision = 10, scale = 2) // precision = 10, up to 10 total digits, scale = 2, two decimal places (cents)
    private BigDecimal cost;

    @Size(max = 50, message = "Nickname must be 50 characters or less.")
    private String nickname;

    @Size(max = 50, message = "Location must be 50 characters or less.")
    private String location;

    @Column(length = 1000)
    @Size(max = 1000, message = "Notes must be 1000 characters or less.")
    private String notes;

    private boolean showNickname;
    private boolean showLocation;

    public CollectionPlant() {}

    public Long getId() {
        return id;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseStore() {
        return purchaseStore;
    }

    public void setPurchaseStore(String purchaseStore) {
        this.purchaseStore = purchaseStore;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isShowNickname() {
        return showNickname;
    }

    public void setShowNickname(boolean showNickname) {
        this.showNickname = showNickname;
    }

    public boolean isShowLocation() {
        return showLocation;
    }

    public void setShowLocation(boolean showLocation) {
        this.showLocation = showLocation;
    }
}

