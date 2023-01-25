package com._52org.gardenoftheheart.application.seed.dto;

import com._52org.gardenoftheheart.application.seed.domain.Seed;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SeedResponseDTO {

    private final String plantName;

    private final Integer growingPeriod;

    private final String description;

    public static SeedResponseDTO toDTO(Seed seed) {

        return new SeedResponseDTO(seed.getPlantName(), seed.getGrowingPeriod(), seed.getDescription());

    }

}
