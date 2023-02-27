package com._52org.gardenoftheheart.application.letter.dto;

import com._52org.gardenoftheheart.application.letter.domain.Letter;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class LetterResponseDTO {

    private final String plantName;

    private final Integer wateringCount;

    private final List<String> keywords;

    public static LetterResponseDTO toDTO(final Letter letter, final List<String> keywordList) {

        return new LetterResponseDTO(letter.getPlant().getPlantName(), letter.getWateringCount(), keywordList);

    }

}
