package com.letter.plant.application.garden.dto;

import com.letter.plant.application.garden.domain.GardenPlant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlantDto {

    private Long letterId;

    private String plantName;

    private int wateringCount;

    private int growingPeriod;

    private boolean isWatered;

    public static PlantDto toDto(GardenPlant gardenPlant) {

        return new PlantDto(
                gardenPlant.getLetterId(),
                gardenPlant.getPlant().getName(),
                gardenPlant.getWateringCount(),
                gardenPlant.getPlant().getGrowingPeriod(),
                gardenPlant.isToday()
        );

    }

}
