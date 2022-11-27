package com.letter.plant.application.letter.dto;

import com.letter.plant.application.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class LetterDetailDto {
    private String plantName;
    private String author;
    private LocalDateTime createDate;
    private String message;

    public static LetterDetailDto toDto(Letter letter) {
        return new LetterDetailDto(
                letter.getPlantName(),
                letter.getAuthor(),
                letter.getCreatedAt(),
                letter.getMessage()
        );
    }
}
