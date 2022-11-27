package com.letter.plant.application.letter.dto;

import com.letter.plant.application.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class LetterWrapperDto {
    private String letterId;
    private String plantName;
    private String author;
    private LocalDateTime createDate;

    public static List<LetterWrapperDto> toDto(List<Letter> letters) {
        return letters.stream()
            .map(letter -> new LetterWrapperDto(
            Long.toString(letter.getId()),
            letter.getPlantName(),
            letter.getAuthor(),
            letter.getCreatedAt()
        )).collect(Collectors.toList());
    }
}
