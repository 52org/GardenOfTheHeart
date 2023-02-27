package com._52org.gardenoftheheart.application.letter.dto;

import com._52org.gardenoftheheart.application.letter.domain.Letter;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@RequiredArgsConstructor
public class LetterListResponseDTO {

    private final Long letterId;

    private final String plantName;

    private final String author;

    private final LocalDate createdAt;

    public static LetterListResponseDTO toDTO(final Letter letter) {

        return new LetterListResponseDTO(letter.getId(), letter.getPlant().getPlantName(), letter.getAuthor(), letter.getCreatedAt().toLocalDate());

    }

}
