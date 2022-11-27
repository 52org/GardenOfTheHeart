package com.letter.plant.application.garden.dto;

import com.letter.plant.application.letter.domain.Keyword;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PlantDetailDto {

    private String plantName;

    private int growingPeriod;

    private int wateringCount;

    private List<String> keywords;

    public static PlantDetailDto toDto(String plantName, int growingPeriod, int wateringCount, List<Keyword> keywords) {
        List<String> keywordList = keywords.stream().map(Keyword::getKeyword).collect(Collectors.toList());

        return new PlantDetailDto(
                plantName,
                growingPeriod,
                wateringCount,
                keywordList
        );
    }

}
