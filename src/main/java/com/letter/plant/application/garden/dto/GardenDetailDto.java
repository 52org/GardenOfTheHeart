package com.letter.plant.application.garden.dto;

import com.letter.plant.application.garden.domain.Plant;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class GardenDetailDto {

    private String name;

    private List<PlantDto> plantList;

    public static GardenDetailDto toDto(String name, List<Plant> plants) {

        List<PlantDto> plantDtoList = plants.stream().map(PlantDto::toDto).collect(Collectors.toList());

        return new GardenDetailDto(name, plantDtoList);

    }

}
