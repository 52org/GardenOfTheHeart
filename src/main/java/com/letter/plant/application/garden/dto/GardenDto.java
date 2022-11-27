package com.letter.plant.application.garden.dto;

import com.letter.plant.application.garden.domain.Garden;
import lombok.Getter;

@Getter
public class GardenDto {

    private String uuid;

    private String name;

    public Garden toEntity() {
        return Garden.builder()
                .uuid(uuid)
                .name(name)
                .build();
    }

}
