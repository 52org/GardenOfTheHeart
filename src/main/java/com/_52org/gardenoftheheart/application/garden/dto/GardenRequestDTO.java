package com._52org.gardenoftheheart.application.garden.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class GardenRequestDTO {

    @NotBlank
    private final String gardenName;

    @NotBlank
    private final String password;

}
