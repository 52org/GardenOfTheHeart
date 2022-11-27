package com.letter.plant.application.letter.dto;

import com.letter.plant.application.letter.domain.Letter;
import lombok.Data;

import java.util.List;

@Data
public class LetterDto {
    private String plantName;
    private String author;
    private String uuid;
    private String message;
    private List<String> keyWords;

    public Letter toEntity() {
        return Letter.builder()
                .plantName(plantName)
                .author(author)
                .message(message)
                .build();
    }
}
