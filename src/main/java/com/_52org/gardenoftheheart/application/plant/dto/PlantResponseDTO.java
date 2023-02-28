package com._52org.gardenoftheheart.application.plant.dto;

import com._52org.gardenoftheheart.application.plant.domain.Plant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class PlantResponseDTO {

    private final String plantName;

    private final Integer growingPeriod;

    private final String description;

    public static PlantResponseDTO toDTO(final Plant plant) {

        return new PlantResponseDTO(plant.getPlantName(), plant.getGrowingPeriod(), plant.getDescription());

    }

}
