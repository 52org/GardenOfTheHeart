package com.letter.plant.application.garden.dto;

import com.letter.plant.application.garden.domain.GardenPlant;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class GardenDetailDto {

    private String name;

    private List<PlantDto> plantList;

    public static GardenDetailDto toDto(String name, List<GardenPlant> gardenPlants) {
        List<PlantDto> plantDtos = new ArrayList<>();

        for (GardenPlant gardenPlant : gardenPlants) {
            plantDtos.add(PlantDto.toDto(gardenPlant));
        }

        return new GardenDetailDto(
            name,
            plantDtos
        );
    }

}
