package org.example.week09.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record MovieRequest(
        @NotBlank @Size(max = 200) String title,
        @NotBlank @Size(max = 100) String director,
        @NotBlank @Size(max = 50) String genre,
        @NotNull @PositiveOrZero Integer runtime,
        @NotNull @DecimalMin("0.0") @DecimalMax("10.0") Double rating,
        @NotNull @PositiveOrZero Integer releaseYear
) { }
