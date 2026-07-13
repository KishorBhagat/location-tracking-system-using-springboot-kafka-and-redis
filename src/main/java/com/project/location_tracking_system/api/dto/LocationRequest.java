package com.project.location_tracking_system.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record
LocationRequest(

        @NotNull
        Double latitude,

        @NotNull
        Double longitude

) {
}
