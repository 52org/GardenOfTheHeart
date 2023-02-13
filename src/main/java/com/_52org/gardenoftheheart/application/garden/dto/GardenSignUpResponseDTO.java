package com._52org.gardenoftheheart.application.garden.dto;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GardenSignUpResponseDTO {

    private final String uuid;

    public static GardenSignUpResponseDTO toDTO(final Garden garden) {

        return new GardenSignUpResponseDTO(garden.getUuid());

    }

}
