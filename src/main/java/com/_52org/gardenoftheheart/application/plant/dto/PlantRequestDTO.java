package com._52org.gardenoftheheart.application.plant.dto;

import com._52org.gardenoftheheart.application.plant.domain.Plant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PlantRequestDTO {

    @NotBlank
    private final String plantName;

    @NotNull
    @Min(1)
    private final Integer growingPeriod;

    @NotBlank
    private final String description;

    public Plant toEntity() {

        return Plant.builder()
                .plantName(plantName)
                .growingPeriod(growingPeriod)
                .description(description)
                .build();

    }

}
