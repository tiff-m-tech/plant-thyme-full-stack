package com.plantthyme.plant_thyme_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProgressPictureUpdateRequest (
        @NotNull(message = "Picture date is required.") // When pic added, it auto uses today's date.
        @PastOrPresent(message = "Picture date cannot be in the future.")
        LocalDate pictureDate,

        @Pattern( // This is for a dropdown selection, adding validation for postman.
                regexp = "(Growth|Repotting|Pruning|Pest Treatment|Other)?",
                message = "Update type must be Growth, Repotting, Pruning, Pest Treatment, or Other."
        )
        String updateType,

        @Size(max = 1000, message = "Notes must be 1000 characters or less.")
        String notes
){
}
