package com.plantthyme.plant_thyme_api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@JsonPropertyOrder({"id", "plant", "purchaseDate", "purchaseStore", "cost", "nickname", "location", "notes"})
public class CollectionPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // No @NotNull here — @Valid runs on the request body before the controller
    // sets plant from the plantId param, so @NotNull would always fail ("plant is
    // required") on a valid request. optional = false enforces it at the DB level instead.
    @ManyToOne(optional = false)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    // Validation constraints (@Size, @PositiveOrZero, @PastOrPresent) are checked by
    // Bean Validation when the controller uses @Valid, and failures return a 400 via
    // GlobalExceptionHandler. @Column annotations are JPA schema config, not validation.

    @PastOrPresent(message = "Purchase date cannot be in the future.")
    private LocalDate purchaseDate;

    @Size(max = 100, message = "Purchase store must be 100 characters or less.")
    private String purchaseStore;

    @PositiveOrZero(message = "Cost cannot be negative.")
    @Column(precision = 10, scale = 2) // precision = 10 = up to 10 total digits, scale = 2 = two decimal places (cents)
    private BigDecimal cost;

    @Size(max = 100, message = "Nickname must be 100 characters or less.")
    private String nickname;

    @Size(max = 100, message = "Location must be 100 characters or less.")
    private String location;

    @Column(length = 1000) // database column
    @Size(max = 1000, message = "Notes must be 1000 characters or less.")
    private String notes;

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
}

