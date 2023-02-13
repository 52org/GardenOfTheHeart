package com._52org.gardenoftheheart.application.garden.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GardenLoginResponseDTO {

    private final TokenDTO tokenDTO;

    private final String uuid;

}
