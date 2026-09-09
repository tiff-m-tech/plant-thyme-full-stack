package com.plantthyme.plant_thyme_api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@JsonPropertyOrder({"id", "name", "imagePath", "light", "water", "fertilize"})
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Plant name is required.")
    @Column(length = 100)
    @Size(max = 100, message = "Plant name must be 100 characters or less.")
    private String name;

    @NotBlank(message = "Image path is required.")
    @Size(max = 255, message = "Image path must be 255 characters or less.")
    private String imagePath;

    @NotBlank(message = "Light instructions are required.")
    @Size(max = 255, message = "Light instructions must be 255 characters or less.")
    private String light;

    @NotBlank(message = "Water instructions are required.")
    @Size(max = 255, message = "Water must be 255 characters or less.")
    private String water;

    @NotBlank(message = "Fertilizing instructions are required.")
    @Size(max = 255, message = "Fertilizing instructions must be 255 characters or less.")
    private String fertilize;

    public Plant() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getFertilize() {
        return fertilize;
    }

    public void setFertilize(String fertilize) {
        this.fertilize = fertilize;
    }
}
