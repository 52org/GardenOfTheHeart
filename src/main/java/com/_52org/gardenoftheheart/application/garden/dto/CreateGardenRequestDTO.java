package com._52org.gardenoftheheart.application.garden.dto;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class CreateGardenRequestDTO {

    @NotBlank
    private final String gardenName;

    @NotBlank
    private final String password;

    public Garden toEntity() {

        return Garden.builder()
                .gardenName(gardenName)
                .password(password)
                .uuid(UUID.randomUUID().toString())
                .build();

    }

}
