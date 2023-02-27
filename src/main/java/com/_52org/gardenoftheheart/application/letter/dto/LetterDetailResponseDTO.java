package com._52org.gardenoftheheart.application.letter.dto;

import com._52org.gardenoftheheart.application.letter.domain.Letter;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@RequiredArgsConstructor
public class LetterDetailResponseDTO {

    private final String plantName;

    private final String author;

    private final String message;

    private final LocalDate createdAt;

    public static LetterDetailResponseDTO toDTO(final Letter letter) {

        return new LetterDetailResponseDTO(letter.getPlant().getPlantName(), letter.getAuthor(), letter.getMessage(), letter.getCreatedAt().toLocalDate());

    }

}
