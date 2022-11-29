package com.letter.plant.application.garden.dto;

import com.letter.plant.application.garden.domain.Plant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlantDto {

    private Long letterId;

    private String plantName;

    private int wateringCount;

    private boolean isWatered;

    public static PlantDto toDto(Plant plant) {

        return new PlantDto(
                plant.getLetterId(),
                plant.getPlantName(),
                plant.getWateringCount(),
                plant.isToday()
        );

    }

}
