package com.plantthyme.plant_thyme_api.dto;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
- Data the client is allowed to send when creating or updating a collection plant.
- Only user-editable fields are included. Fields like id, plant, and progressPictures are left out on purpose, so a request can't set or change them.
- Validation happens here first (via @Valid in the controller). The same rules on the CollectionPlant entity act as a safety net when Hibernate saves to the database.
*/

public record CollectionPlantRequest(
        @PastOrPresent(message = "Purchase date cannot be in the future.")
        LocalDate purchaseDate,

        @Size(max = 50, message = "Purchase store must be 50 characters or less.")
        String purchaseStore,

        @PositiveOrZero(message = "Cost cannot be negative.")
        BigDecimal cost,

        @Size(max = 50, message = "Nickname must be 50 characters or less.")
        String nickname,

        @Size(max = 50, message = "Location must be 50 characters or less.")
        String location,

        @Size(max = 1000, message = "Notes must be 1000 characters or less.")
        String notes,

        Boolean showNickname,
        Boolean showLocation
) {}