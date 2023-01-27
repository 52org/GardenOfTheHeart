package com._52org.gardenoftheheart.application.garden.dto;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GardenResponseDTO {

    private final String uuid;

    public static GardenResponseDTO toDTO(final Garden garden) {

        return new GardenResponseDTO(garden.getUuid());

    }

}
